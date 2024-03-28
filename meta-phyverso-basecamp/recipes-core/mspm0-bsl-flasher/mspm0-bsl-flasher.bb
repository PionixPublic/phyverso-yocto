DESCRIPTION = "MSPM0 BSL UART flash utility"

# has to be fixed someday
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

#LICENSE = "CLOSED"
#LIC_FILE_CHKSUM = ""


SRC_URI = "git://github.com/rckstrh/mspm0_bsl_flasher;protocol=https;branch=main"
SRCREV = "caba5464ad25e16e02675f3c6133fd65619a9097"
SRC_URI[sha256sum] = "e06d00de493123883f665a3b4c207c0412227a9c324f340bfbbf7958c0313a0c"

DEPENDS = "boost"

S = "${WORKDIR}/git"

inherit cmake

do_install() {
    install -d ${D}${bindir}
    install -m 0755 MSPM0_bsl_flasher ${D}${bindir}
}
