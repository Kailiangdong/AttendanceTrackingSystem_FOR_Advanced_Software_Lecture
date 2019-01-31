#import sys
import subprocess
from time import sleep
import pifacecad
from urllib import request, parse
import requests
import xml.etree.ElementTree as ET
import re
cad = pifacecad.PiFaceCAD()

SCANQR_CMD = "./go.sh"
POST_URL='http://my-first-project-222110.appspot.com/rest/attendance/post/xml'

arrow=pifacecad.LCDBitmap([
    0b00100,
    0b01110,
    0b10101,
    0b00100,
    0b00100,
    0b00100,
    0b00100,
    0b00100])
cad.lcd.store_custom_bitmap(0,arrow)

def run_cmd(cmd):
    return subprocess.check_output(cmd, shell=True).decode('utf-8')

def decode_QR():
    return run_cmd(SCANQR_CMD)
   
def select_w_g(event):
  global week, group, arrow
  ## Set week 1-13,
  FLAG_5=0
  event.chip.lcd.clear()
  while True:
      if event.chip.switches[7].value==1:
          if week <13:
              week+=1
          else:
              week=1
              event.chip.lcd.clear()
          FLAG_6=0
      elif event.chip.switches[6].value==1:
          if week >1:
              if week==10:
                  event.chip.lcd.clear()
              week-=1
          else:
              week=13
              event.chip.lcd.clear()
          FLAG_6=0
      elif event.chip.switches[5].value==1 and FLAG_5==1:
          FLAG_5=0
          event.chip.lcd.clear()
          break    
      event.chip.lcd.set_cursor(0,0)
      event.chip.lcd.write("Week:%s Group:%s" % (week, group))
      event.chip.lcd.set_cursor(5,1)
      event.chip.lcd.write_custom_bitmap(0)
      if event.chip.switches[5].value==0:
          FLAG_5=1
      if event.chip.switches[0].value==1:
          event.chip.lcd.clear()
          event.chip.lcd.set_cursor(0,0)
          event.chip.lcd.write("Week:%s Group:%s" % (week, group))
          return
  ## Set group 1-6      
  while True:
      if event.chip.switches[7].value==1:
          if group <6:
              group+=1
          else:
              group=1
          FLAG_6=0
      elif event.chip.switches[6].value==1:
          if group >1:
              group-=1
          else:
              group=6
          FLAG_6=0
      elif event.chip.switches[5].value==1 and FLAG_5==1:
          break
      event.chip.lcd.set_cursor(0,0)
      event.chip.lcd.write("Week:%s Group:%s" % (week, group))
      if week>9:
          event.chip.lcd.set_cursor(14,1)
      else:
          event.chip.lcd.set_cursor(13,1)
      event.chip.lcd.write_custom_bitmap(0)
      if event.chip.switches[5].value==0:
          FLAG_5=1
      if event.chip.switches[0].value==1:
          event.chip.lcd.clear()
          event.chip.lcd.set_cursor(0,0)
          event.chip.lcd.write("Week:%s Group:%s" % (week, group))
          return
    
   
def scan(event):
  global group, week

  event.chip.lcd.clear() 
  event.chip.lcd.set_cursor(0, 0)
  event.chip.lcd.write("Scanning...")
  
  str_read=decode_QR()
  
  event.chip.lcd.clear() 
  event.chip.lcd.set_cursor(0, 0)
  event.chip.lcd.write("Scan successed")
  event.chip.lcd.set_cursor(0, 1)
  event.chip.lcd.write("Sending data...")
  
##parse string
  id_read=re.findall(r"id=(.+?) ",str_read)[0]
  token_read=re.findall(r"token=(.+)",str_read)[0]

## POST in XML
  data="<record><student_id>%s</student_id><token>%s</token><group>%s</group><week>%s</week></record>" % (id_read,token_read,group,week)
  headers={'Content-Type':'application/xml'}
  resp=requests.post(POST_URL, data=data, headers=headers)
  root = ET.fromstring(resp.text)
  if root[0].text=='ERROR':
      event.chip.lcd.clear()
      event.chip.lcd.set_cursor(0, 0)
      event.chip.lcd.write("Failure")
      event.chip.lcd.set_cursor(0, 1)
      event.chip.lcd.write(root[1].text)
  elif root[0].text=='SUCCESS':
      event.chip.lcd.clear()
      event.chip.lcd.set_cursor(0, 0)
      event.chip.lcd.write("SUCCESS")
  


if __name__ == "__main__":
    cad.lcd.blink_off()
    cad.lcd.cursor_off()

    week=1
    group=1
    
    cad.lcd.backlight_on()    
    cad.lcd.clear()
    cad.lcd.set_cursor(0,0)
    cad.lcd.write("Week:%s Group:%s" % (week, group))

    listener = pifacecad.SwitchEventListener(chip=cad)
    listener.register(0, pifacecad.IODIR_FALLING_EDGE, scan)
    listener.register(5, pifacecad.IODIR_FALLING_EDGE, select_w_g)
    listener.activate()
    
    

    
