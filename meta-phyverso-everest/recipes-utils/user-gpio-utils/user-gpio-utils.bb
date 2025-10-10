DESCRIPTION = "Exports a selected set of PhyVerso user GPIOs as named symlinks to be used with gpio sysfs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = " \
	file://user-gpio-exporter.pl \
    file://export-user-gpios.service \
"

inherit systemd

SYSTEMD_AUTO_ENABLE:${PN} = "enable"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "export-user-gpios.service"

do_install:append() {
    install -d ${D}/root/user_gpios
    install -m 0644 ${WORKDIR}/user-gpio-exporter.pl ${D}/root/user_gpios/
    chmod +x ${D}/root/user_gpios/user-gpio-exporter.pl

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/export-user-gpios.service ${D}${systemd_system_unitdir}
}

FILES:${PN} += "/root/user_gpios"
FILES:${PN} += "${systemd_system_unitdir}/export-user-gpios.service"
RDEPENDS:${PN} = "perl"