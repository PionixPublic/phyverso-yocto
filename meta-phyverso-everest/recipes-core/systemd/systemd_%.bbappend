# use this file and *.network files under meta-phyverso-everest/recipes-core/systemd/systemd/
# to overwrite *_mcan0.network files, to change bitrates for example

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += " \
    file://main_mcan0.network \
    file://mcu_mcan0.network \
"

PHYVERSO_MAIN_MCAN0_BITRATE ??= "250000"
PHYVERSO_MCU_MCAN0_BITRATE ??= "250000"

do_install:append() {
    sed -i 's/MAIN_MCAN0_BITRATE/${PHYVERSO_MAIN_MCAN0_BITRATE}/g' ${D}${systemd_unitdir}/network/main_mcan0.network
    sed -i 's/MCU_MCAN0_BITRATE/${PHYVERSO_MCU_MCAN0_BITRATE}/g' ${D}${systemd_unitdir}/network/mcu_mcan0.network
}