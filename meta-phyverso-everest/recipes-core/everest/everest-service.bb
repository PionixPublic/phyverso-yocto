LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "file://everest.service"

FILES:${PN} += " \
    ${systemd_system_unitdir}/everest.service \
"

inherit systemd

SYSTEMD_AUTO_ENABLE:${PN} = "enable"
SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE:${PN} = "everest.service"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/everest.service ${D}${systemd_system_unitdir}
}

RDEPENDS:${PN} = "everest-core"