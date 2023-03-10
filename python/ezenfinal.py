%%writefile ezenfinal.py
#!pip install cx_Oracle
#!pip install flask_sqlalchemy

from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask import request
from flask import jsonify
import json
import pandas as pd

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False

#####################
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
###################
def prod_recommend(userid):
    #!pip install flask_sqlalchemy
    #!pip install cx_Oracle
    import cx_Oracle
    import pandas as pd
    import numpy as np

    # 오라클 데이터베이스 연결
    connection = cx_Oracle.connect('scott/TIGER@localhost:1521/XE')

    # 커서 생성
    cursor = connection.cursor()

    # SQL 쿼리 실행
    query1 = 'SELECT * FROM tb_order'
    query2 = 'SELECT * FROM userjoin'
    query3 = 'SELECT * FROM goods'
    cursor.execute(query1)

    # 결과를 데이터프레임으로 변환
    df_order = pd.read_sql(query1, connection)

    cursor.execute(query2)
    df_user = pd.read_sql(query2, connection)

    cursor.execute(query3)
    df_goods = pd.read_sql(query3, connection)


    # 커넥션과 커서 닫기
    cursor.close()
    connection.close()


    # 분석을 위한 데이터 프레임 
    df_merged = pd.merge(df_order, df_user[['USERID', 'BIRTH', 'GENDER']], on='USERID', how='inner')
    df_merged1 = pd.merge(df_merged, df_goods[['GOODSNUM','CATEGORY']], on='GOODSNUM', how='inner')

    # 생일에서 년도만 분리
    df_merged1['BIRTH_YEAR'] = pd.to_datetime(df_merged['BIRTH'], format='%Y-%m-%d').dt.strftime('%Y')

    # 필요한 컬럼 추출
    df= df_merged1.loc[:, ['BIRTH_YEAR','GENDER', 'GOODSNUM','CATEGORY','USERID']]
    df

    import gower
    from sklearn.cluster import DBSCAN
    # 범주형 데이터 이진형으로 변환
    df_categorical = pd.get_dummies(df[['GENDER', 'GOODSNUM']])

    # 수치형 데이터와 변환된 범주형 데이터를 합침
    df_all = pd.concat([df[['USERID', 'BIRTH_YEAR']], df_categorical], axis=1)
    df_all

    gower_matrix = gower.gower_matrix(df)
    gower_matrix

    # DBSCAN 클러스터링
    dbscan = DBSCAN(eps=0.5, min_samples=5, metric='precomputed')
    try:
        dbscan.fit(gower_matrix)
        labels = dbscan.labels_
        labels
    except:
        print("데이터 없음")
        return "There is No Data"

    df_gower = pd.DataFrame(gower_matrix, index=df['USERID'], columns=df['USERID'])
    df_gower

    from sklearn.cluster import DBSCAN
    dbscan = DBSCAN(eps=0.4, min_samples=3, metric='precomputed')
    dbscan.fit(df_gower)
    dbscan.labels_
    df['cluster']=dbscan.labels_
    df
    df.sort_values(by=['cluster'])

    # 가장 많이 팔린 4개 상품
    top4 = df_goods['GOODSNUM'].value_counts().nlargest(4).index.tolist()
    print(top4)
    
    recommend = []

    try:
        user=userid
        cluster_user = df.loc[df['USERID']==user, 'cluster'].values[0]
    except:
        print("예외: 구매목록없음")
        recommend=top4
        return recommend
    else:
        # userid=user인 사람의 cluster 값을 가져옴
        cluster_user = df.loc[df['USERID']==user, 'cluster'].values[0]
        
        # cluster가 동일한 그룹의 distinct(prod_num)를 구함
        A = set(df.loc[df['cluster']==cluster_user, 'GOODSNUM'].unique())
        
        # {userid} 인 사람이 구매하지 않은 GOODSNUM 구함
        not_purchased = A - set(df.loc[df['USERID']==user, 'GOODSNUM'].unique())
        print("rec_for_user:",not_purchased)

        # 리스트로 변환하고 내부 요소를 정수형으로 변환
        rec = [int(x) for x in not_purchased]                
        
        if len(rec)!=0:
            recommend = rec

        else:
            recommend = top4
        
        print("recommen:",recommend)
        return recommend
#####################################

#다루한
@app.route("/chatGPT",methods=['POST'])
def chatGPT():
    js = request.get_json()
    gender = js['gender']
    current_weight = float(js['current_weight'])
    goal_weight = float(js['goal_weight'])
    response = chatGPT1(f"몸무게 : {current_weight}, 목표 체중 : {goal_weight}kg, 성별 : {gender}인 사람에게 하루 식단을 추천해주고 각 식단의 칼로리도 알려줘.")
    answer = response.split("\n")
    print(type(answer))
    print(answer)
    return json.dumps(answer)

#다루한
@app.route("/meal_calc", methods=['POST'])
def meal_calc():
    js = request.get_json()
    df = pd.read_excel('통합 식품영양성분DB_음식_20230302.xlsx',usecols=[5,11,12,15,19,26,32,48])
    print(js)
    result = df[df['식품명'].isin(js['식품명'])]
    return result.to_json()

#상욱
@app.route("/prod_recommend", methods=['POST'])
def prodRecommend():
    json = request.get_json()
    userid = json['userid']
    print("userid:"+userid)
    return prod_recommend(userid)
    
    
app.run(host='0.0.0.0',debug=True,port=7878)