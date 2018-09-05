#! /bin/bash

/usr/sbin/tinyproxy

echo -e "vpnbook\n${VPN_PASSWD}" > /opt/vpnbook/realm.txt

/usr/sbin/openvpn --config /opt/vpnbook/books/vpnbook-fr1-tcp80.ovpn --auth-user-pass /opt/vpnbook/realm.txt