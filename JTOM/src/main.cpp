#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <ArduinoJson.h>

const char* ssid = "Anea";
const char* password = "762988tis";
// const char* ssid = "Inther";
// const char* password = "inth3rmoldova";

const char* id = "1x323124"; 
volatile long userId = 777;

const int PUSH_BET_BUTTON = 12;
volatile bool betButtonState;
uint8_t bet_btn_prev;

const int PUSH_FINISH_BUTTON = 13;
volatile bool finishButtonState;
uint8_t finish_btn_prev;

const int RED_PIN = 5;
volatile bool redLedState;

const int GREEN_PIN = 4;
volatile bool greenLedState;

const int BLUE_PIN = 14;
volatile bool blueLedState;

// String serverApiBet = "http://172.17.41.32:8080/api/iot/bet";
// String serverApiFinish = "http://172.17.41.32:8080/api/iot/finish";
String serverApiBet = "http://192.168.0.103:8080/api/iot/bet";
String serverApiFinish = "http://192.168.0.103:8080/api/iot/finish";

void setup() {
  Serial.begin(115200); 

  pinMode(PUSH_BET_BUTTON, INPUT_PULLUP);
  bet_btn_prev = digitalRead(PUSH_BET_BUTTON);

  pinMode(PUSH_FINISH_BUTTON, INPUT_PULLUP);
  finish_btn_prev = digitalRead(PUSH_FINISH_BUTTON);

  pinMode(GREEN_PIN, OUTPUT);
  pinMode(RED_PIN, OUTPUT);
  pinMode(BLUE_PIN, OUTPUT);

  WiFi.begin(ssid, password);
  Serial.println("Connecting");
  while(WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    analogWrite(RED_PIN, 0);
    analogWrite(GREEN_PIN, 0);
    analogWrite(BLUE_PIN, 1);
    delay(150);
  }
  int count = 0;
  while(count !=3) {
    count++;
    // RGB
  }

  Serial.println("");
  Serial.print("Connected to WiFi network with IP Address: ");
  Serial.println(WiFi.localIP());

  Serial.println("Timer set to 5 seconds (timerDelay variable), it will take 5 seconds before publishing the first reading.");
}

void RGB_color(int red_light_value, int green_light_value, int blue_light_value)
 {
  analogWrite(RED_PIN, red_light_value);
  analogWrite(GREEN_PIN, green_light_value);
  analogWrite(BLUE_PIN, blue_light_value);
}

static bool isButtonPressedDebounced(int BUTTON_PIN)
{
    constexpr int dwellTimeMs = 50;
    static unsigned int buttonStateChangedTimeStamp = 0;

    static bool prevReadState;
    bool readState = !digitalRead(BUTTON_PIN);
    
    if(readState != prevReadState)
    {
      buttonStateChangedTimeStamp = millis();
      prevReadState = readState;
    }

    if(millis() - buttonStateChangedTimeStamp > dwellTimeMs)
    {
      return readState == HIGH;
    }

    return LOW;
}


void loop() {

  // static bool prevBetButtonState;
  // finishButtonState = digitalRead(PUSH_FINISH_BUTTON);
  static int buttonEventTimeStamp = millis();

  if (isButtonPressedDebounced(PUSH_BET_BUTTON)) {
    //Check WiFi connection status
    if(WiFi.status()== WL_CONNECTED){
      WiFiClient client;
      HTTPClient http;

      String serverPath = serverApiBet;

        // Your Domain name with URL path or IP address with path
        http.begin(client, serverPath.c_str());
        
        // Send HTTP GET request
        int httpResponseCode = http.POST(id);
        
        if (httpResponseCode == 200) {
          Serial.print("HTTP Response code: ");
          Serial.println(httpResponseCode);
          String payload = http.getString();
          Serial.println(payload);

          StaticJsonDocument<600> doc;            
          deserializeJson(doc, payload);

          // deserializeJson(doc, http.getStream());
          const char* status = doc["status"];
        }
        else {
            Serial.print("Error code: ");
            Serial.println(httpResponseCode);
        }

        buttonEventTimeStamp = millis();
        if(httpResponseCode == 200) {
          RGB_color(2, 5, 29);
        }
        else {
          RGB_color(255, 110, 5);
        }

        http.end();
        // Free resources
    }
    else {
      Serial.println("WiFi Disconnected");
    } 
  }
  else if (isButtonPressedDebounced(PUSH_FINISH_BUTTON)) {
    //Check WiFi connection status
    if(WiFi.status()== WL_CONNECTED){
      WiFiClient client;
      HTTPClient http;

      String serverPath = serverApiFinish;

      // Your Domain name with URL path or IP address with path
      http.begin(client, serverPath.c_str());
      
      // Send HTTP GET request
      int httpResponseCode = http.POST(id);
      
      if (httpResponseCode == 200) {
        Serial.print("HTTP Response code: ");
        Serial.println(httpResponseCode);
        String payload = http.getString();
        Serial.println(payload);

        StaticJsonDocument<600> doc;            
        deserializeJson(doc, payload);

        // deserializeJson(doc, http.getStream());
        const char* status = doc["status"];
        Serial.println(strcmp(status, "FINISHED") == 0);

        if(strcmp(status, "FINISHED") == 0) {
          int i = 0;
          while(i != 35) {
            i++;
            if(i < 11) {
              
            }
            else {
              
            }
          }
        }
      }
      else {
          Serial.print("Error code: ");
          Serial.println(httpResponseCode);
      }

      buttonEventTimeStamp = millis();
      if(httpResponseCode == 200) {
          RGB_color(1, 1, 5);
      }
      else {
          RGB_color(255, 110, 5);
      }

      http.end();
      // Free resources
    }
    else {
      Serial.println("WiFi Disconnected");
    } 
  }

  if(millis() - buttonEventTimeStamp > 5000) {
    RGB_color(255, 255, 0);
  }

  bet_btn_prev = digitalRead(PUSH_BET_BUTTON);
  finish_btn_prev = digitalRead(PUSH_FINISH_BUTTON);
}
