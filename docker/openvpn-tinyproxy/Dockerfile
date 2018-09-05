FROM debian:stable-slim

EXPOSE 8888

RUN apt-get update && apt-get install -y openvpn tinyproxy

COPY config /

ENV VPN_PASSWD=**None**

CMD ["/opt/vpnbook/run.sh"]
