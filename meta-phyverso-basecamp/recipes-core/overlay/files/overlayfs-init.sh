#!/bin/bash

if [ -e "/overlay/OVERLAY_CLEAN" ]; then
    echo "Cleaning flag detected, delete the overlay folders and reinitialize the file structure ..."
    rm -rf /overlay/etc_work
    rm -rf /overlay/etc_upper
    rm -rf /overlay/var_work
    rm -rf /overlay/var_upper
    rm -rf /overlay/logs
    # don't forget to delete the flag
    rm -f /overlay/OVERLAY_CLEAN
fi

if [ -d "/overlay/etc_work" ]; then
    echo "Overlay /etc file structure already exists, skipping creation."
else
    echo "Create overlay /etc file structure ..."
    mkdir -p -m 0755 /overlay/etc_work
    mkdir -p -m 0755 /overlay/etc_upper
fi

if [ -d "/overlay/var_work" ]; then
    echo "Overlay /var file structure already exists, skipping creation."
else
    echo "Create overlay /var file structure ..."
    mkdir -p -m 0755 /overlay/var_work
    mkdir -p -m 0755 /overlay/var_upper
    mkdir -p -m 0755 /overlay/logs
fi
if [ -d "/overlay/logs" ]; then
    echo "Overlay /log file structure already exists, skipping creation."
else
    echo "Create overlay /log file structure ..."
    mkdir -p -m 0755 /overlay/logs
fi
echo "Overlay file structure initialization complete."

echo "Mount the overlay."
mount -t overlay overlay -o lowerdir=/etc,upperdir=/overlay/etc_upper,workdir=/overlay/etc_work /etc
mount -t overlay overlay -o lowerdir=/var,upperdir=/overlay/var_upper,workdir=/overlay/var_work /var
echo "Done."
