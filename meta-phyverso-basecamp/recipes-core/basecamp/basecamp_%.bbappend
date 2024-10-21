FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
           file://config-phyverso-ac-hlc.yaml \
           file://config-phyverso-ac-pwm.yaml \
           file://config-phyverso-ac-dc.yaml \
           file://config-phyverso-ac-kit.yaml \
           "

FILES:${PN} += " \
                ${sysconfdir}/everest/* \
               "

do_install:append() {
    install -d ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-hlc.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-pwm.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-dc.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-kit.yaml ${D}${sysconfdir}/everest/
    ln -s -r ${D}${sysconfdir}/everest/config-phyverso-ac-kit.yaml ${D}${sysconfdir}/everest/basecamp.yaml
}
