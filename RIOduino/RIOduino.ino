#include <Wire.h>
#include <Adafruit_NeoPixel.h>
#include <avr/power.h>

#define ELEVATOR_NEO_PIN 6
#define ELEVATOR_NUM_PIXELS 60

Adafruit_NeoPixel elevatorPixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);

int selectedLightPattern = 0;
int animationState = 0;

void setup(){
  Wire.begin(1);
  Wire.onReceive(receiveEvent);
  elevatorPixels.begin();  
}

void loop(){
  
  switch(selectedLightPattern){
    case 0:
      setSolidColor(elevatorPixels, 0, 0, 0);
      break; 
     case 1:
       setChase(elevatorPixels, 0, 255, 255, animationState);
       animationState++;
       if(animationState > 1000) animationState = 0;
       break;
  }
  
  delay(200);
  
}

void setChase(Adafruit_NeoPixel pixels, int r, int g, int b, int state){
  int numPixels = pixels.getPixels();
  state = state / 10;
   for(int i = 0; i < numPixels; i ++){
     
   }
}

void setSolidColor(Adafruit_NeoPixel pixels, int r, int g, int b){
  for(int i = 0; i < pixels.getPixels(); i++){
   pixels.setPixelColor(i, pixels.Color(r, g, b)); 
  }
}

void receiveEvent(int howMany){
  while(1 < Wire.available()){
    char c = Wire.read(); // receive byte as a character
  }
  
  int x = Wire.read();    // receive byte as an integer
  
  selectedLightPattern = x;
  animationState = 0;
}
