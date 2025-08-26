BUILD_TAG ??= "UNTAGGED"

def run_git(d, cmd):
    try:
        oeroot = d.getVar('COREBASE', True)
        return bb.process.run("git --work-tree %s/../../phyverso-yocto/ --git-dir %s/../../phyverso-yocto/.git %s"
            % (oeroot, oeroot, cmd))[0].strip('\n')
    except:
        pass

python() {
    layer_rev = run_git(d, 'rev-parse HEAD')
    if layer_rev:
        d.setVar('LAYER_REV', layer_rev)

    build_tag = run_git(d, 'describe --tags --exact-match')
    if build_tag:
        d.setVar('BUILD_TAG', build_tag)
}

OS_RELEASE_FIELDS:append = " BUILD_ID BUILD_TAG LAYER_REV"

VERSION = "EVerest phyVERSO-EVCS ${BUILD_TAG} ${@'(%s)' % DISTRO_CODENAME if 'DISTRO_CODENAME' in d else ''}"
PRETTY_NAME = "${VERSION}"

# Ensure the git commands run every time bitbake is invoked.
BB_DONT_CACHE = "1"

do_compile[nostamp] = "1"
do_install[nostamp] = "1"