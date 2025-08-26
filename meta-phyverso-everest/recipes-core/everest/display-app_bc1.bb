SUMMARY = "Flutter PIONIX Display App"
DESCRIPTION = "Flutter PIONIX Display App"
AUTHOR = "PIONIX"
HOMEPAGE = "https://github.com/PionixPro/display-app"
BUGTRACKER = "https://customer.support.pionix.com/jira/software/c/projects/DA/boards/565"
SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# Default SRCREV and SRC_URI
SRCREV = "7ac2b87641fa761ee920b86326d033e64d41baab"
SRC_URI = "${GIT_REPOSITORY_URL}/${PREFIX_GIT_REPOSITORY}display-app.git;branch=development;protocol=ssh \
           file://display-app.service.in \
          "

inherit flutter-app
inherit features_check
inherit systemd

S = "${WORKDIR}/git"

SRCREV = "7e3e5feff8d0ac7aaff978992d0446b62ddad159"
do_configure[network] = "1"
do_compile[network] = "1"

PR = "r0"

# Override SRCREV and SRC_URI if OVERWRITE_DISPLAY_APP_REF is set
GIT_BASE_URI = "git://git@github.com/PionixPro/display-app.git;protocol=ssh"

python () {
    # when using BASECAMP_UNSTABLE, we want to use the latest commit of development branch by default
    if d.getVar('BASECAMP_UNSTABLE', True) == "1":
        d.setVar('SRCREV', "${AUTOREV}")
        d.setVar('PV', "1.0+git${SRCPV}")
    
    # Handle OVERWRITE_DISPLAY_APP_REF
    display_app_ref = d.getVar('OVERWRITE_DISPLAY_APP_REF')
    if display_app_ref:
        git_base_uri = d.getVar('GIT_BASE_URI')
        display_app_ref = display_app_ref.strip()
        if len(display_app_ref) == 40 and all(c in '0123456789abcdefABCDEF' for c in display_app_ref):
            # It's a commit hash
            d.setVar('SRCREV', display_app_ref)
            git_uri = "%s;nobranch=1" % git_base_uri
        else:
            # Assume it's a branch
            d.setVar('SRCREV', '${AUTOREV}')
            git_uri = "%s;branch=%s" % (git_base_uri, display_app_ref)
            d.setVar('PV', "1.0+git${SRCPV}")
        src_uri = "%s file://display-app.service.in" % git_uri
        d.setVar('SRC_URI', src_uri)
}

PUBSPEC_APPNAME = "display_app"
FLUTTER_APPLICATION_INSTALL_PREFIX = "/usr/share/flutter"
FLUTTER_BUILD_ARGS = "bundle"

REQUIRED_DISTRO_FEATURES= " systemd"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
SYSTEMD_SERVICE:${PN} = "display-app.service"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    
    FLUTTER_PI_ARGS=""
    
    if [ -n "${DISPLAY_APP_DIMENSIONS}" ]; then
        FLUTTER_PI_ARGS="${FLUTTER_PI_ARGS} --dimensions \"${DISPLAY_APP_DIMENSIONS}\""
    fi
    
    sed "s|@@ARGS@@|${FLUTTER_PI_ARGS}|g" ${WORKDIR}/display-app.service.in > ${D}${systemd_system_unitdir}/display-app.service
    
    chmod 0644 ${D}${systemd_system_unitdir}/display-app.service
    ln -sr ${D}${FLUTTER_INSTALL_DIR}/${FLUTTER_SDK_VERSION}/release ${D}${FLUTTER_INSTALL_DIR}/app
}

FILES:${PN} += "${systemd_system_unitdir}/display-app.service"
