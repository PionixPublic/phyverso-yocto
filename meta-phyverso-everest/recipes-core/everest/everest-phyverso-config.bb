DESCRIPTION = "phyVERSO specific EVerest configurations"
LICENSE = "CLOSED"

RDEPENDS:${PN}="everest-core"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
           file://config-phyverso-ac-hlc.yaml \
           file://config-phyverso-ac-pwm.yaml \
           file://config-phyverso-ac-dc.yaml \
           file://config-phyverso-ac-kit.yaml \
           file://config-phyverso-template.yaml \
           file://config-phyverso-evcs-cube.yaml \
           file://isabellenhuette-network.conf \
           file://provision-ac-kit.sh \
           file://provision-dc-kit.sh \
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
    install -m 0644 ${WORKDIR}/config-phyverso-evcs-cube.yaml ${D}${sysconfdir}/everest/
    mkdir -p ${D}${sysconfdir}/everest/provisioning/
    mkdir -p ${D}${sysconfdir}/systemd/network/10-eth0.network.d/
    install -m 0644 ${WORKDIR}/isabellenhuette-network.conf ${D}${sysconfdir}/everest/provisioning/
    install -m 0744 ${WORKDIR}/provision-ac-kit.sh ${D}${sysconfdir}/everest/provisioning/
    install -m 0744 ${WORKDIR}/provision-dc-kit.sh ${D}${sysconfdir}/everest/provisioning/
}