SYSTEMD_SERVICE:${PN}:remove = "everest.service"

FILES:${PN} += "${systemd_system_unitdir}/"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        rm ${D}${systemd_system_unitdir}/everest.service
    fi
}

# only used temporarily until PhyVersoBSP for FW 2.0 is merged into main and meta-everest branch updated
SRC_URI = "git://github.com/EVerest/everest-core.git;branch=feat/phyverso-extend-config-options-and-error-flags;protocol=https  \
           file://everest.service \
           "
SRCREV = "6ab54b482e2e01992a1ea566e06f29da15204f65"
