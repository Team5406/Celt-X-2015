#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIN 6
#define NUMPIXELS 60

#define RED_PIN 9
#define BLUE_PIN 10
#define GREEN_PIN 11

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);

String desiredPattern = "rainbow";
int patternState = 0;

int redVal = 0;
int greenVal = 0;
int blueVal = 0;

int delayval = 100;

void setup() {
  
  Wire.begin(1);
  Wire.onReceive(receiveEvent);
  
  
  pixels.begin();
}

void loop(){
  if(desiredPattern.equals("rainbow")){
      rainbow(pixels, patternState);
      patternState++;
      if(patternState >= 256) patternState = 0;
  }
  else if(desiredPattern.equals("red")){
       setSolidColor(pixels, pixels.Color(255, 0, 0));
       patternState = 0;
  }
  else if(desiredPattern.equals("green")){
       setSolidColor(pixels, pixels.Color(0, 255, 0));
       patternState = 0;
  }
  else if(desiredPattern.equals("blue")){
       setSolidColor(pixels, pixels.Color(0, 0, 255));
       patternState = 0;
  }
  else{
       setSolidColor(pixels, pixels.Color(0, 255, 0)); 
       patternState = 0;
  }
  
  setUnderglow();
  
  delay(delayval);
}

void receiveEvent(int howMany){
  char message[32];
  for(int i = 0; Wire.available(); i++){
    char c = (char)Wire.read();
    if((int)(c) > (int)(' ')){
      message[i] = (c); 
    }
  }
  
  int index = 0;
  
  String values[4];
  int valIndex = 0;
  
  for(int i = 0; i < 32; i++){
    if(message[i] == NULL) break;
    if(message[i] == ':'){
        for(int j = index; j < i; j++){
          values[valIndex] += message[j];
        }
        valIndex++;
        index = i + 1;
    }
  }
  
  desiredPattern = values[0];
  redVal = atoi(values[1].c_str());
  greenVal = atoi(values[2].c_str());
  blueVal = atoi(values[3].c_str());
}

void setUnderglow(){
  analogWrite(RED_PIN, redVal);
  analogWrite(GREEN_PIN, greenVal);
  analogWrite(BLUE_PIN, blueVal);
}


// Fill the dots one after the other with a color
void colorWipe(Adafruit_NeoPixel pixels, int c, int wait) {
  for(uint16_t i=0; i < pixels.numPixels(); i++) {
      pixels.setPixelColor(i, c);
      pixels.show();
      delay(wait);
  }
}

void setSolidColor(Adafruit_NeoPixel pixels, int c){
  for(uint16_t i=0; i<pixels.numPixels(); i++) {
      pixels.setPixelColor(i, c);
  }
  pixels.show();
}

void rainbow(Adafruit_NeoPixel pixels, int state) {
  for(int i = 0; i < pixels.numPixels(); i++) {
    pixels.setPixelColor(i, Wheel((i * state / pixels.numPixels()) & 255));
  }
  pixels.show();
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
