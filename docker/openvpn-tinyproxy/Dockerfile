FROM debian:stable-slim

EXPOSE 8888

RUN apt-get update && \
    apt-get install --no-install-recommends -y openvpn tinyproxy

COPY config /

RUN chmod +x /opt/vpnbook/run.sh

ENV VPN_PASSWD=**none** \
    VPN_BOOK=vpnbook-ca1-tcp443.ovpn

VOLUME /opt/vpnbook/books

CMD ["/opt/vpnbook/run.sh"]
