DESCRIPTION = "PIONIX PoC Display App"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN} = " \
    flutter-pi \
    flutter-engine \
    display-app \
"
