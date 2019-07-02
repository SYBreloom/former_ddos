# coding=utf-8
from lxml import etree
import urllib2
import time
import sys


class SflowClawer():

    def get_result(self):
        try:
            html = urllib2.urlopen('http://localhost:8008/agents/html').read()
            # html = open('D:\zju毕设\data\sflow.html'.decode('utf-8'), 'r').read()
            node_tree = etree.HTML(html)
            keys = node_tree.xpath('//tbody/tr/th/text()')
            values = node_tree.xpath('//tbody/tr/td/text()')

            result = {}
            for i in range(len(keys)):
                result[keys[i]] = values[i]
            print result
        except Exception, e:
            print e
            return ""

    def write_to_file(self, path, txt):
        with open(path, 'a') as f:
            f.write(txt)
            f.close()

    def main_process(self, sec, path):
        while True:
            result = self.get_result()
            self.write_to_file(path, result + '\n')
            time.sleep(sec)


if __name__ == '__main__':
    sc = SflowClawer()
    sc.main_process(int(sys.argv[1]), sys.argv[2])

