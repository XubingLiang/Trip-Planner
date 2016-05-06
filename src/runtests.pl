#! /usr/bin/perl

$counter = 1;
@files = <testcases/*>;
@files = grep { $_ !~ /^testcases\/(:?userout-|output-)/ } @files;

if ($ARGV[0]) {
	$args = $ARGV[0];
	if ($args =~ /f/) {
		@files = ($ARGV[1]);
	} elsif ($args =~ /(\d*)-(\d*)/) {
		$start = $1 || 1;
		$end = $2 || @files;
		@files = @files[$start - 1 .. $end - 1];
		$counter = $start;
	} elsif ($args =~ /(\d+)/) {
		@files = @files[$1 - 1 .. $1 - 1];
		$counter = $1;
	}
	if ($args =~ /p/) {
		$printout = 1;
	}
	if ($args =~ /s/) {
		$stop = 1;
	}
}
for $file (@files) {
	$userOutputFile = $file;
	$userOutputFile =~ s/(.*\/)?(.*)$/\1userout-\2/;
	$outputFile = $file;
	$outputFile =~ s/(.*\/)?(.*)$/\1output-\2/;
	if ($ARGV[0] =~ /f/) {
		$outputFile = $ARGV[2] if $ARGV[2]; 
		$userSupplied = $ARGV[3];
	}
	%transfers = ();
	%times = ();
	%trips = ();
	@input = ();
	open $testCase, "<", $file or die "Could not find file $file";
	while ($line = <$testCase>) {
		push @input, $line;
		@arguments = split " ", $line;
		if ($arguments[0] eq "Transfer") {
			$transfers{$arguments[2]} = $arguments[1];
		} elsif ($arguments[0] eq "Time") {
			$times{$arguments[2].$arguments[3]} = $arguments[1];
			$times{$arguments[3].$arguments[2]} = $arguments[1];
		} elsif ($arguments[0] eq "Trip") {
			$trips{"$arguments[1].$arguments[2]"}++;
		}
	}
	close $testCase;
	$input = join '', @input;
	open $tekOutput, "<", "$outputFile" or die "Could not find file $outputFile\n";
	$teknumtrips = 0;
	@tekout = ();
	while ($line = <$tekOutput>) {
		push @tekout, $line;
		if ($line =~ /^(\d+) nodes expanded/) {
			$teknodes = $1;
		} elsif ($line =~ /cost = (\d+)/) {
			$tekcost = $1;
		} elsif ($line =~ /^Trip \w+ to \w+/) {
			$teknumtrips++;
		}
	}
	$tekout = join "", @tekout; 
	close $tekOutput;

	if ($userSupplied) {
		open $f, "<", $userSupplied or die "counldnt open";
		$userOutput = join "", <$f>;
		close $f;
	} else {
		$userOutput = `java TripPlanner $file`;
	}
	if ($?) {
		print "----- $file -----\n\n";
		print "\n*** Input ***\n$input\n\n";
		print "*** Output ***\n$userOutput\n";
		print "The program errored on input file $fileName\nExit Code: $?\n";
		print "Press enter to continue to the next test case or ctrl-c to exit\n";
		<STDIN>;
		next;
	}

	open $toFile, ">", $userOutputFile or die "Could not open file $userOutputFile\n";
	print $toFile $userOutput;
	close $toFile;
	@userTrips = ();
	for $line (split /\n/, $userOutput) {
		if ($line =~ /^(\d+) nodes expanded\s*$/) {
			$usernodes = $1;
		} elsif ($line =~ /^cost = (\d+)\s*$/) {
			$usercost = $1;
		} elsif ($line =~ /^Trip \w+ to \w+\s*$/) {
			push @userTrips, $line;
		}
	}
	$mismatchFlag = 0;
	@messages = ();
	push @messages, "Nodes expanded: Output File - $teknodes, You - $usernodes\n";
	if ($usercost != $tekcost) {
		push @messages, "✗ *** MISMATCH *** Cost: Output File - $tekcost, You - $usercost\n";
		$mismatchFlag = 1;
	} else {
		push @messages, "✓ Cost matches: cost = $usercost\n";
	}
	$usernumtrips = scalar @userTrips;
	if ($usernumtrips != $teknumtrips) {
		push @messages, "✗ *** MISMATCH *** Number of trips: Output File - $teknumtrips, You - $usernumtrips\n";
		$mismatchFlag = 1;
	} else {
		push @messages, "✓ Number of trips matches: numTrips = $usernumtrips\n";
	}
	$useractualcost = -1 * $transfers{"London"};
	$prev = '';
	$notlinked = 0;
	for $trip (@userTrips) {
		($from, $to) = $trip =~ /^Trip (\w+) to (\w+)/;
		$useractualcost += $transfers{$from} + $times{$from.$to};
		$trips{"$from.$to"}-- if exists $trips{"$from.$to"};
		$notlinked++ if $prev and $prev ne $from;
		$prev = $to;
	}
	$useractualcost = 0 if $useractualcost < 0;
	@missingtrips = ();
	for $key (keys %trips) {
		while ($trips{$key} > 0) {
			push @missingtrips, $key;
			$trips{$key}--;
		}
	}
	if (scalar @missingtrips > 0) {
		push @messages, "✗ *** ERROR *** The following trips were not made:\n";
		for $m (@missingtrips) {
			push @messages, join " ", "\tTrip", split /\./, $m;
			push @messages, "\n";
		}
		$mismatchFlag = 1;
	} else {
		push @messages, "✓ All required trips present\n";
	}
	if ($notlinked) {
		push @messages, "✗ *** ERROR *** There are $notlinked occurrances where a trip does not link up with the next trip\n";
		$mismatchFlag = 1;
	} else {
		push @messages, "✓ All trips are linked\n";
	}
	if ($useractualcost != $usercost) {
		push @messages, "✗ *** ERROR *** The cost of your trip seems to be calculated wrong. Actual cost: $useractualcost\n";
		$mismatchFlag = 1;
	} else {
		push @messages, "✓ Cost seems to be calculated correctly\n";
	}
	if ($userTrips[0] !~ /^Trip\s*London/) {
		push @messages, "✗ *** ERROR *** You did not start in london\n";
		$mismatchFlag = 1;
	} else {
		push @messages, "✓ Started in London\n";
	}
	print "----- $counter - $file -----\n\n";
	$counter++;
	if ($mismatchFlag) {
		print "*** Input ***\n$input\n\n";
		print "*** Output File ***\n$tekout\n*** Your output ***\n$userOutput\n\n";
		if ($userTrips[0] =~ /^Trip London/ and not $notlinked and $useractualcost == $usercost and $usercost < $tekcost) {
			print "*****************\n";
			print "*** ATTENTION *** Output file is likely to be wrong as you produced valid output with a lower cost\n";
			print "*****************\n\n";
		}
		print join '', @messages;
		print "Press enter to continue or ctrl-c to exit\n";
		<STDIN>;
	} else {
		print "*** Input ***\n$input\n\n*** Your output ***\n$userOutput\n\n" if ($printout);
		print join '', @messages;
		print "Looks right!\n\n";
		if ($stop) {
		print "(-s flag) Press enter to continue or ctrl-c to exit\n";
			<STDIN>;
		}
	}
}
