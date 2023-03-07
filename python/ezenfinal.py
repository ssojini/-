%%writefile ezenfinal.py
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask import request
from flask import jsonify

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False

def chatGPT1(prompt):
    import openai

    # API key 설정
    openai.api_key = "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"

    # API 호출
    completion = openai.Completion.create(
        engine="text-davinci-003",
        prompt=prompt,
        max_tokens=1024,
        n=1,
        stop=None,
        temperature=0.5,
    )

    # API 응답 처리
    message = completion.choices[0].text
    print(message)
    
    return message

@app.route("/chatGPT",methods=['POST'])
def chatGPT():
    json = request.get_json()
    gender = json['gender']
    current_weight = float(json['current_weight'])
    goal_weight = float(json['goal_weight'])
    answer = chatGPT1(f"몸무게 : {current_weight}, 목표 체중 : {goal_weight}kg, 성별 : {gender}인 사람에게 하루 식단을 추천해주고 각 식단의 칼로리도 알려줘.")
    print("answer:"+answer)
    return answer
    
app.run(host='0.0.0.0',debug=True,port=7878)