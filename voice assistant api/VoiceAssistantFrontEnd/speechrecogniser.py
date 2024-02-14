# module for speech recognition 
# from ast import Global
from cgitb import text
# import queue
from sys import maxsize
# from typing import Text
import speech_recognition as sr
# module for multithreading 
import threading
# from threading import Event 

# module for multiprocesssing
import multiprocessing
# module for pyaudio
import pyaudio
# module for queue data type -- used for communication btw threads
from queue import Queue
# imported actions method from test_to_speech.py
from speaking import actions
# module for searching string 
import re




# callback function of speech recognition 
def callback(recognizer, audio):
    
    try:
        
        
        # recognizes the speech and converts it in text 
        text=recognizer.recognize_google(audio).lower()
        print("you said " + text)
        # q.put(txt)
        print(q.empty())
        # queue.put(text)
        # q.put(text)
        if q.empty()==True :
            print("is emptyyy")
            q.put(text)
            print(q.empty())
       
        else :
            print("not emptyyy")
            q.get(text)
            q.put(text)
        # p1=threading.Thread(target=wake_word )
        # p1.daemon=False
        # p1.start()
        # checks whether wake word is spoken or not 
        # x = re.findall("zenius|xenius|genius", txt)
        # print(x)

        # if x : 
        #     str=""
        #     wake_word=str.join(x)
        #     print("inside if ")
        #     #  removes everything said before wakeword  
        #     command=txt.split(wake_word)[1].lstrip()
        #     print(command)
                

        #     txt_queue.put(command)
        #     print(txt_queue.get(command))
         
    except sr.UnknownValueError:
        print("could not understand audio")
        txt="hello how are you "
        q.put(txt)
        # text = input("enter something")
        # print(q.empty())
        # # queue.put(text)
        # # q.put(text)
        # if q.empty()==True :
        #     print("is emptyyy")
        #     q.put(text)
        #     print(q.empty())
       
        # else :
        #     print("not emptyyy")
        #     q.get(text)
        #     q.put(text)
            
        
    
    except sr.RequestError as e:
        print("Could not request results Speech Recognition service; {0}".format(e))


def speaker():
    print('number of current threads is ', threading.active_count())
        
    # p1=threading.Thread(target=wake_word )
    #  if a thread is daemon it terminates or stops as main function exits .
    # p1.daemon=False
    #  starting thread
    p2=threading.Thread(target=pro  )
    # if p1.is_alive() :
    #     print("p1 joining ")
    #     p1.join()
    p2.daemon=False
    # p1.start()
    if p2.is_alive():
        print("p2 joining ")
        p2.join()
    p2.start()
    
    print("joining .....")
def wakethread() :
    p1=threading.Thread(target=wake_word )
    p1.daemon=True
    p1.start()




def wake_word() : 
    # global text
    txt=q.get(text)
    print(txt)
    print('number of current threads is ', threading.active_count())
        
        
    
    
    x = re.findall("zenius|xenius|genius", txt)

    print(x)
    if x : 
        print("inside wake ")
        str=""
        wake_command=str.join(x[0])
        txt=txt.replace(wake_command,'')
        print(wake_command)
        
        global command
        command=txt.split(wake_command)[0].lstrip()
        print(type(command))
        print(command)
        q1.put(command)
            
    # command="Now to generate the database schema we'll need to pass the schema"
    
    
    print("hello ") 
    wake_word()
    



def pro() : 
    
    # text= print(queue.get(text))

    # global command    
    print("main")
    
    command= q1.get(text)
    if __name__=='__main__' :
        
        print("hello" +command)
        # using multiprocess to start speech . 
        p = multiprocessing.Process(target=actions  , args=(command,))
        p.start()
        
        # keep checking for stop , stops the speech in middle of an utterance 
        while p.is_alive():
            # print("process called ")
            # print(text)
            # print(text )
            if q1.empty() != True: 

                print("terminaing ")

                p.terminate()

                print("terminated")

                # speaker()
                
            else:
                continue

        p.join()
        
        speaker()


# creating recognizer obj 
r = sr.Recognizer()

mic = sr.Microphone()

# microphone as a source for audio 
with mic as source :
    # pause threshold - seconds of non-speaking audio before a phrase is considered complete
    r.pause_threshold=0.8
    #  adjust for noise in audio , cancelling the noise , taking one second for identifying noise 
    r.adjust_for_ambient_noise(source , duration=1)

print("listening................... ")
# creates a thread for keep listening in background and calls callback function for recognizing audio
r.listen_in_background(mic, callback)
maxsize = 2 
q = Queue(maxsize)
q1= Queue()
# creating another thread for calling speak function and further process 


speaker()
wakethread()   