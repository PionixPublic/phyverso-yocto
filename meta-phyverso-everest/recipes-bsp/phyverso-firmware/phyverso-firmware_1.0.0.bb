FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DESCRIPTION = "phyVERSO MCU firmware binary"

LICENSE = "CLOSED"

SRC_URI = "file://phyverso-firmware.bin"

FILES:${PN} += "${libdir}/firmware/phyverso-firmware.bin"

do_install() {
    install -d ${D}${libdir}/firmware/
    install -m 0644 ${WORKDIR}/phyverso-firmware.bin ${D}${libdir}/firmware/
}