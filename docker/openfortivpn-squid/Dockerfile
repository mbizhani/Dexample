FROM debian:testing-20181112-slim

EXPOSE 9999

RUN apt-get update && \
  apt-get install --no-install-recommends -y ppp openfortivpn squid3

COPY config /

RUN chmod +x /opt/vpn/run.sh

ENV OVPN_CFG=q

VOLUME /opt/vpn/cfg

CMD ["/opt/vpn/run.sh"]
