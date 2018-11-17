#!/bin/bash

/usr/sbin/squid

mkdir -p /opt/vpn/cfg

/usr/bin/openfortivpn -c /opt/vpn/cfg/${OVPN_CFG}
