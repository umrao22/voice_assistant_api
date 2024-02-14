# module for speech recognition 
from typing import Text
import speech_recognition as sr
# module for multithreading 
import threading
# module for multiprocesssing
import multiprocessing
# module for pyaudio
import pyaudio
# module for queue data type -- used for communication btw threads
from queue import Queue
# from speaking import actions
# imported actions method from test_to_speech.py
from text_to_speech import actions
# module for searching string 
import re
import sounddevice as sd

# print(sd.query_devices()) 
# p = pyaudio.PyAudio()
# info = p.get_host_api_info_by_index(0)
# numdevices = info.get('deviceCount')
# for i in range(0, numdevices):
#         if (p.get_device_info_by_host_api_device_index(0, i).get('maxInputChannels')) > 0: 
            
#             print("Input Device id ", i, " - ", p.get_device_info_by_host_api_device_index(0, i).get('name'))
# callback function of speech recognition 
def callback(recognizer, audio):
    
    try:
        
        
        # recognizes the speech and converts it in text 
        txt=recognizer.recognize_google(audio).lower()
        print("you said " + txt)
        # txt="hello how are you its working In this Python tutorial, we will show you how to take voice input with microphone in Python using PyAudio and SpeechRecogniti"
        # q.put(txt)
        
        # checks whether wake word is spoken or not 
        x = re.findall("zenius|xenius|genius", txt)
        print(x)

        if x : 
            print("inside wake ")
            str=""
            wake_command=str.join(x[0])
            txt=txt.replace(wake_command,'')
            print(wake_command)
            
            
            command=txt.split(wake_command)[0].lstrip()
            print(type(command))
            print(command)
            q.put(command)
            # print(q.get(command))
         
    except sr.UnknownValueError:
        print("could not understand audio")
        text=" could not recognize,  please say again In this Python tutorial, we will show you how to take voice input with microphone in Python using PyAudio and SpeechRecogniti "
        # queue.put(text)
        # q.put(text)
       


    except sr.RequestError as e:
        print("Could not request results Speech Recognition service; {0}".format(e))



def pro() : 
    
    # text= print(queue.get(text))

    print('number of current threads is ', threading.active_count())    
    print("main")
    text= q.get(Text)
    
    if __name__=='__main__' :
        
        print("hello" +text)
        # using multiprocess to start speech . 
        p = multiprocessing.Process(target=actions  , args=(text,))
        p.start()
        
        # keep checking for stop , stops the speech in middle of an utterance 
        while p.is_alive():
            # print("process called ")
            # print(text)
            # print(text , q.empty())
            if q.empty() != True:  

                print("terminaing ")

                p.terminate()

                print("terminated")

                speaker()
                
            else:
                continue

        p.join()
        
        speaker()
def get_speakers_index(list_microphone_names):
    list_index = []
    for i in range(len(list_microphone_names)):
        if "speakers" in list_microphone_names[i].lower():
            list_index.append(i)
    print(list_index)        
    return list_index
# creating recognizer obj 
r = sr.Recognizer()
# for index, name in enumerate(sr.Microphone.list_microphone_names()):
#     print("Microphone with name \"{1}\" found for `Microphone(device_index={0})`".format(index, name))
mic = sr.Microphone(device_index=1)
# list_index = []
# list_index = sr.Microphone.list_microphone_names()
# print(list_index)
# microphone as a source for audio 
with mic as source :
    # pause threshold - seconds of non-speaking audio before a phrase is considered complete
    r.pause_threshold=0.8
    #  adjust for noise in audio , cancelling the noise , taking one second for identifying noise 
    r.adjust_for_ambient_noise(source , duration=1)

print("listening................... ")
# creates a thread for keep listening in background and calls callback function for recognizing audio
r.listen_in_background(mic, callback)

q = Queue()

# creating another thread for calling speak function and further process 
def speaker():

    print('number of current threads is ', threading.active_count())
        
    p1=threading.Thread(target=pro )
    #  if a thread is daemon it terminates or stops as main function exits .
    p1.daemon=False
    #  starting thread
    p1.start()

speaker()
    