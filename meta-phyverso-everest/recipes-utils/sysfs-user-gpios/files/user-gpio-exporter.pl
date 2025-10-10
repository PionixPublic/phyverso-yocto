#!/usr/bin/env perl

# match labels specified in user_gpio_labels to named GPIOs in device tree,
# export matched gpio lines to /sys/class/gpio/gpio-<line number> and
# make symbolic link in current directory with
# gpio labels to be able to access gpio lines by name
#
# important: exported pins will be claimed by sysfs and wont be usable via EVerest or libgpiod tools
my @user_gpio_labels = (LIST_OF_LABELS);

my $gpio_info_path = "/sys/kernel/debug/gpio";
open(my $in, "<", $gpio_info_path) or die "Can't open $gpio_info_path: $!";

foreach my $line ( <$in> ) {
    if ($line =~ /\sgpio-(\d{3}).\((.\S+)/) {
        if($2 ~~ @user_gpio_labels) {
            print "Exporting $2 (gpio$1)\n";
            `echo $1 > /sys/class/gpio/export`;
            `ln -s /sys/class/gpio/gpio$1 /root/user_gpios/$2`;
        }
    }
}
