#!/bin/bash

echo "nameserver 8.8.8.8" > /etc/resolv.conf

/usr/sbin/tinyproxy

echo -e "vpnbook\n${VPN_PASSWD}" > /opt/vpnbook/realm.txt

mkdir -p /dev/net
if [ ! -c /dev/net/tun ]; then
  mknod /dev/net/tun c 10 200
fi

/usr/sbin/openvpn --config /opt/vpnbook/books/${VPN_BOOK} --auth-user-pass /opt/vpnbook/realm.txt