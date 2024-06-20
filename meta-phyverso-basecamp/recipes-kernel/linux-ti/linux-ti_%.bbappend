FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
	file://overlayfs.cfg \
	file://usb_ch341.cfg \
	file://cc33xx_2nd_loader.bin \
	file://cc33xx-conf.bin \
	file://cc33xx_fw.bin \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install:append () {
    install -d ${D}/lib/firmware/ti-connectivity/
    install -m 0644 ${WORKDIR}/cc33xx_2nd_loader.bin ${D}/lib/firmware/ti-connectivity/cc33xx_2nd_loader.bin
    install -m 0644 ${WORKDIR}/cc33xx-conf.bin ${D}/lib/firmware/ti-connectivity/cc33xx-conf.bin
    install -m 0644 ${WORKDIR}/cc33xx_fw.bin ${D}/lib/firmware/ti-connectivity/cc33xx_fw.bin
}

FILES:${PN} = " \
    /lib/firmware/ti-connectivity/cc33xx_2nd_loader.bin \
    /lib/firmware/ti-connectivity/cc33xx-conf.bin \
    /lib/firmware/ti-connectivity/cc33xx_fw.bin \
"