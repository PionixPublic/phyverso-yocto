DESCRIPTION = "phyVERSO specific Basecamp configurations"
LICENSE = "CLOSED"

RDEPENDS:${PN}="basecamp"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
           file://config-phyverso-ac-hlc.yaml \
           file://config-phyverso-ac-pwm.yaml \
           file://config-phyverso-ac-dc.yaml \
           file://config-phyverso-ac-kit.yaml \
           file://config-phyverso-template.yaml \
           file://config-phyverso-dc-kit.yaml \
           file://isabellenhuette-network.conf \
           "

FILES:${PN} += " \
                ${sysconfdir}/everest/* \
               "

do_install() {
    install -d ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-hlc.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-pwm.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-dc.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-ac-kit.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-template.yaml ${D}${sysconfdir}/everest/
    install -m 0644 ${WORKDIR}/config-phyverso-dc-kit.yaml ${D}${sysconfdir}/everest/
    mkdir -p ${D}${sysconfdir}/everest/provisioning/
    install -m 0644 ${WORKDIR}/isabellenhuette-network.conf ${D}${sysconfdir}/everest/provisioning/
}