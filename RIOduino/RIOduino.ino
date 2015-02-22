#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIN 6
#define NUMPIXELS 60

#define RED_PWM 9
#define BLUE_PWM 10
#define GREEN_PWM 11

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(60, PIN, NEO_GRB + NEO_KHZ800);

String desiredPattern = 0;
uint16_t patternState = 0;

int redVal = 0;
int greenVal = 0;
int blueVal = 0;

uint32_t delayval = 100;

void setup() {
  
  Wire.begin(1);
  Wire.onReceive(receiveEvent);

  pixels.begin();
}

void loop(){
  if(strcmp(desiredPatter, "rainbow")){
      rainbow(patternState);
      patternState++;
      if(patternState >= 256) patternState = 0;
  }
  else if(strcmp(desiredPattern, "red"){
       setSolidColor(pixels.Color(255, 0, 0));
  }
  else if(strpcmp(desiredPattern, "green"){
       setSolidColor(pixels.Color(0, 255, 0));
  }
  else if(strcmp(desiredPattern, "blue"){
       setSolidColor(pixels.Color(0, 0, 255));
  }
  else{
       setSolidColor(pixels.Color(0, 255, 0)); 
  }
  
  setUnderglow();
  
  delay(delayval);
}

void receiveEvent(int howMany){
  String message = '';
  
  while(Wire.available()){
    char c = (char)Wire.read();
    if((int)(c) > (int)(' ')){
      message += c; 
    }
  }
  
  String[] values = splitString(message, ':');
  
  desiredPattern = values[0];
  redVal = atoi(values[1];
  greenVal = atoi(values[2]);
  blueVal = atoi(values[3]);
}


String[] splitString(String text, char splitChar) {
  int splitCount = countSplitCharacters(text, splitChar);
  String returnValue[splitCount];
  int index = -1;
  int index2;

  for(int i = 0; i < splitCount - 1; i++) {
    index = text.indexOf(splitChar, index + 1);
    index2 = text.indexOf(splitChar, index + 1);

    if(index2 < 0) index2 = text.length() - 1;
    returnValue[i] = text.substring(index, index2);
  }

  return returnValue;
}

void setUnderglow(){
  analogWrite(RED_PIN, redVal);
  analogWrite(GREEN_PIN, greenVal);
  analogWrite(BLUE_PIN, blueVal);
}


// Fill the dots one after the other with a color
void colorWipe(uint32_t c, uint8_t wait) {
  for(uint16_t i=0; i<pixels.numPixels(); i++) {
      pixels.setPixelColor(i, c);
      pixels.show();
      delay(wait);
  }
}

void setSolidColor(uint32_t c){
  for(uint16_t i=0; i<pixels.numPixels(); i++) {
      pixels.setPixelColor(i, c);
  }
  pixels.show();
}

void rainbow(int state) {
  for(int i = 0; i < pixels.numPixels(); i++) {
    pixels.setPixelColor(i, Wheel((i * state / pixels.numPixels()) & 255));
  }
  pixels.show();]
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
   return pixels.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  } else if(WheelPos < 170) {
    WheelPos -= 85;
   return pixels.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  } else {
   WheelPos -= 170;
   return pixels.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
  }
}
