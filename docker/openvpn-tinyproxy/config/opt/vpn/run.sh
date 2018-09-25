#!/bin/bash

echo "nameserver 8.8.8.8" > /etc/resolv.conf

/usr/sbin/tinyproxy

#echo -e "vpnbook\n${VPN_PASSWD}" > /opt/vpnbook/realm.txt

mkdir -p /dev/net
if [ ! -c /dev/net/tun ]; then
  mknod /dev/net/tun c 10 200
fi

mkdir -p /opt/vpn/cfg

/usr/sbin/openvpn --config /opt/vpn/cfg/${OVPN_CFG} --auth-user-pass /opt/vpn/cfg/${OVPN_RLM}
