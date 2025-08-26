require recipes-images/images/phyverso-basecamp-base.bb
SUMMARY =  "Image for phyVERSO, no specific charging hardware configuration"

# fixme: change symlink dest once basecamp merge is done
add_config_symlink() {
    ln -s -r ${IMAGE_ROOTFS}/etc/everest/config-phyverso-template.yaml ${IMAGE_ROOTFS}/etc/everest/basecamp.yaml
}
ROOTFS_POSTPROCESS_COMMAND:append = " add_config_symlink; "