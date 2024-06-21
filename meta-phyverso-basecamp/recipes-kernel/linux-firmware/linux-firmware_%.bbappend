FILESEXTRAPATHS:prepend := "${THISDIR}/linux-firmware:"
SRC_URI += "\
    file://cc33xx_2nd_loader.bin \
	file://cc33xx-conf.bin \
	file://cc33xx_fw.bin \
"

do_install:append () {
    install -d ${D}/lib/firmware/ti-connectivity/
    install -m 0644 ${WORKDIR}/cc33xx_2nd_loader.bin ${D}/lib/firmware/ti-connectivity/cc33xx_2nd_loader.bin
    install -m 0644 ${WORKDIR}/cc33xx-conf.bin ${D}/lib/firmware/ti-connectivity/cc33xx-conf.bin
    install -m 0644 ${WORKDIR}/cc33xx_fw.bin ${D}/lib/firmware/ti-connectivity/cc33xx_fw.bin
}

# NOTE: Use "=+" instead of "+=". Otherwise, the file is placed into the linux-firmware package.
PACKAGES =+ "${PN}-cc33xx"
FILES:${PN}-cc33xx = " \
    /lib/firmware/ti-connectivity/cc33xx_2nd_loader.bin \
    /lib/firmware/ti-connectivity/cc33xx-conf.bin \
    /lib/firmware/ti-connectivity/cc33xx_fw.bin \
"