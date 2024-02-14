# text to speech module

import pyttsx3
import json
import requests
import spacy

# process start executing the actions function 
nlp = spacy.load('en_core_web_sm')
stopwords = nlp.Defaults.stop_words
def actions(text): 

    if text==" " or "" :
        speak("hello , how are you  ")


    else :
        # hiting the api and fetching response to speak 
        l=[]
        mk=["keys here"]
        keys= {"main_keys":l, "keys" : mk }
        
        for token in text.split():
            if token.lower() not in stopwords:    #checking whether the word is not 
                l.append(token)
        url = "http://localhost:7004/qrySrch"

        payload = json.dumps({

             "query": ""+text+"",
             "keywords" : keys

                 })
        headers = {
        'Content-Type': 'application/json'
        }

        response = requests.request("POST", url, headers=headers, data=payload)
        result=json.loads(response.text)

        print(type(response.text))


        print(result['MESSAGE'])           
        speak(result['MESSAGE'])
        print(text)

# speech function . 
engine=pyttsx3.init()
engine.setProperty("rate", 112) 
def speak(text): 
    print("trying to speak ")
    # engine=pyttsx3.init()
    # engine.setProperty("rate", 112)
    engine.say(text)
    # engine.startLoop
    engine.runAndWait()


