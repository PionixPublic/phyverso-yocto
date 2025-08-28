phytec-dev-ca
=============

> [!WARNING]
> ### For demonstration purposes only!
> All passwords, keys, certificates and other files in this repository are for
> demonstration purposes only! Never use these keys and certificates in a
> productive environment! This repository is publicly available and is **not**
> meant for securing private devices!

Dependencies
------------
phytec-dev-ca is a development CA created with the tool
[XCA](https://hohnstaedt.de/xca).

The password for the database is set to `phytec-dev-ca`.

The keys for NXP's HAB v4 and AHAB were created using the scripts provided in
NXP's Code Signing Tool:
[gitlab.apertis.org/pkg/imx-code-signing-tool](https://gitlab.apertis.org/pkg/imx-code-signing-tool/-/tree/debian/unstable)

Certificate Hierarchy
---------------------

* `main-ca`: PHYTEC-ROOT is a self signed certificate with RSA-4096
  * `nxp_ahab_pki`: NXP keys for AHAB secure boot with NIST P-521 (secp521r1)
    * bootloader/boot container: for i.MX93
    * Super root keys (SRK) 1, 2, 3 and 4
    * Certificates for SRKs signed by PHYTEC Root CA
  * `nxp_habv4_pki`:  NXP Keys for HABV4 (SRK 1..4) secure boot with RSA-4096
    * bootloader/boot container: for i.MX6, i.MX6UL, i.MX8M (MNP)
    * fitImage: with u-boot for i.MX8M (MNP)
    * CSF
    * IMG
  * `ti_k3`: Keys for secure boot for TI K3 architecture devices
    * phytecSMPK: An RSA-4096 dummy Secondary Manufacture Public Key. This key is used
                  for signing or encrypting images.
    * phytecSMEK: An RSA-4096 dummy Secondary Manufacture Encryption Key. This key is
                  used for encrypted boot on the secured device.
    * phytecBMPK: An RSA-4096 dummy Back-up Manufacture Public Key. This key is used
                  for signing or encrypting images.
    * phytecBMEK: An RSA-4096 dummy Back-up Manufacture Encryption Key. This key is
                  used for encrypted boot on the secured device.
    * phytecAES256: An AES-256 dummy key used for encrypted boot.
    * ti-degenerate-key: An RSA-4096 key used to encrypt the keys that you are burning
                         onto the device.
  * `fit`: PHYTEC-FIT4096 for signing FIT-Images with RSA-4096
    * used for i.MX6, i.MX6UL with barebox
  * `rauc-intermediate`: PHYTEC-rauc Intermediate CA with RSA-2048
    * `development-1`: PHYTEC-RAUC-Dev1 for signing rauc update bundles with RSA-2048
  * `rauc-intermediate-crypt`: PHYTEC-RAUC-CRYPT Intermediate CA for signing crypt device certificates with RSA-4096
    * `PHYTEC-RAUC-CRYPT-recipients`: Device certificates for encrypting the rauc update bundle with RSA-4096
  * `kernel_modsign`: PHYTEC-modsign for signing kernel modules with RSA-4096

* `rauc`: self-signed CA only for rauc (old one)
  * `development-1`: PHYTEC-RAUC-Dev1 for signing rauc update bundles with RSA-2048


