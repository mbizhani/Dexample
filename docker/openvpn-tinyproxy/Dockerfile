FROM debian:stable-slim

EXPOSE 8888

RUN apt-get update && \
    apt-get install --no-install-recommends -y openvpn tinyproxy

COPY config /

RUN chmod +x /opt/vpn/run.sh

ENV OVPN_RLM=q \
    OVPN_CFG=q

VOLUME /opt/vpn/cfg

CMD ["/opt/vpn/run.sh"]
