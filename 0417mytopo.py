# -*- coding:utf-8 -*-

# Author: $￥
# @Time: 2019/4/17 我也不知道几点写的


from mininet.topo import Topo
from mininet.net import Mininet
from mininet.node import Controller, RemoteController
from datetime import datetime
from mininet.cli import CLI
import time, os, sys, logging, shutil,threading, imp

hosts = []
mapping = {}
threads = []
exp_hosts = [101,106,114,118]
controller_ip = '10.15.123.112'
#controller_ip = '192.168.203.168'


host_cmds = {
    # 'tcpreplay --intf1=h104-eth0 --multiplier=0.5 10secondsNormal/(v3/)_subnet1.pcap'

    'h101': 'tcpreplay --intf1=h101-eth0 10secondsNormal_test_sub1.pcap',
    'h106': 'tcpreplay --intf1=h106-eth0 10secondsNormal_test_sub2.pcap',
    'h114': 'tcpreplay --intf1=h114-eth0 10secondsNormal_test_sub3.pcap',
    'h118': 'tcpreplay --intf1=h118-eth0 10secondsNormal_test_sub4.pcap'
}
mac_maps = {
    'h101': '00:11:25:bb:1f:cf',
    'h102': '00:11:25:bb:ce:a1',
    'h103': '00:11:25:bb:ce:df',
    'h104':'00:11:25:b9:ac:22',
    'h105':'00:11:25:5f:9b:4f',
    'h106':'00:11:25:b9:a7:ed',
    'h107':'00:11:25:bb:d2:6f',
    'h108':'00:02:55:bf:75:a9',
    'h109':'00:09:6b:03:e8:a4',
    'h110':'00:09:6b:e9:a9:00',
    'h111':'00:09:6b:8b:63:8e',
    'h112':'00:09:6b:e9:cf:6b',
    'h113':'00:09:6b:e9:d0:88',
    'h114':'00:0d:60:96:ac:5e',
    'h115':'00:0d:60:96:ac:f7',
    'h116':'00:0d:60:96:ad:25',
    'h117':'00:09:6b:d8:7f:9a',
    'h118':'00:0d:60:96:ac:f6',
    'h119':'00:02:55:7f:70:de',
    'h120':'00:11:25:bb:cf:0f',
    'h121':'00:0d:60:96:ae:5c',
    'h122': '00:22:19:20:7b:d3',  # MAC of 122
    'h123': '00:09:6b:d8:f2:c8',
    'h124':'00:e0:b1:87:f5:94'
}

def do_send_packet(net, host):
    print time.time()
    print net[host].cmd(host_cmds[host])


def send_packet_timer(net, host):
    now_time = datetime.now()
    day_ = str(now_time.date().year) + '-' + str(now_time.date().month) + '-' + str(now_time.date().day)
    imp.acquire_lock()
    start_time = datetime.strptime(day_ + ' ' + sys.argv[1] + ':00', "%Y-%m-%d %H:%M:%S") # 后面不用argv了，直接min_timer$￥
    imp.release_lock()
    timer_start_time = (start_time - now_time).total_seconds()
    print timer_start_time
    timer = threading.Timer(timer_start_time, do_send_packet, (net, host, ))
    timer.start()
    timer.join()


class mytopo(Topo):
    def __init__(self, **opts):
        Topo.__init__(self, **opts)
        switch1 = self.addSwitch('s1')
        switch2 = self.addSwitch('s2')
        switch3 = self.addSwitch('s3')
        switch4 = self.addSwitch('s4')
        switch5 = self.addSwitch('s5')
        switch6 = self.addSwitch('s6')
        
        for i in range(101, 106):
            host = self.addHost('h'+str(i), ip='192.168.1.' + str(i), mac=mac_maps['h'+str(i)])
            self.addLink(host, switch1)
            hosts.append(host)
        for i in range(106, 114):
            host = self.addHost('h'+str(i),  ip='192.168.2.' + str(i), mac=mac_maps['h'+str(i)])
            self.addLink(host, switch2)
            hosts.append(host)
        for i in range(114, 118):
            host = self.addHost('h'+str(i),  ip='192.168.3.' + str(i), mac=mac_maps['h'+str(i)])
            self.addLink(host, switch3) # $￥
            hosts.append(host)
        for i in range(118, 122):
            host = self.addHost('h'+str(i),  ip='192.168.4.' + str(i), mac=mac_maps['h'+str(i)])
            self.addLink(host, switch4)
            hosts.append(host)
        for i in range(122, 125):
            host = self.addHost('h' + str(i), ip='192.168.5.' + str(i), mac=mac_maps['h'+str(i)])
            self.addLink(host, switch6)
            hosts.append(host)

        self.addLink(switch1, switch5)
        self.addLink(switch2, switch5)
        self.addLink(switch3, switch5)
        self.addLink(switch4, switch5)
        self.addLink(switch5, switch6)




if __name__ == '__main__':
    mtopo = mytopo()
    net = Mininet(topo=mtopo,controller = None,build = False)

    controller = RemoteController('c', ip=controller_ip, port=6633)
    net.addController(controller)

    net.start()

    for i in exp_hosts:
      t = threading.Thread(target=send_packet_timer, args=(net, 'h' + str(i),))
      threads.append(t)
      t.start()

    for t in threads:
       t.join()

    CLI(net)

    net.stop()
