# KYdotsDemo
Stack used: Java8, Elastic Search(6.2), Web Services, Guice 

Created a rest api with two resources.
CollegeResource - It is used to crawl the dat from the website link passed in the payload and then its data (Gmat score, college name is stored in ES).

StudentResource: It is used to read to the file which is given in the input and updates the data in ES, when the data is read,
before storing the data in ES a call is made to the method which check whether the college details of the applied college is mentioned in the ES if yes then we store the status is stored as selected or rejected otherwise the status is applied.

Used Jsoup to crawl the website data.

Ex: StudentDetails in ES

{
  "took": 6,
  "timed_out": false,
  "_shards": {
    "total": 3,
    "successful": 3,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 1,
    "hits": [
      {
        "_index": "studentdetails",
        "_type": "studentdetails",
        "_id": "5859629e0b38aae16c4f8b8834a1d196",
        "_score": 1,
        "_source": {
          "name": "surabhi gupta",
          "pursuing": "mba",
          "backgroundDetails": [
            {
              "courseName": "B.tech",
              "collegeName": " ECE",
              "percentage": " Jaypee University of Engineering and Technology"
            },
            {
              "courseName": "12th",
              "collegeName": " St. Francis’ Convent College",
              "percentage": " 81%"
            },
            {
              "courseName": "10th",
              "collegeName": " St. Francis’ Convent College",
              "percentage": " 85%"
            }
          ],
          "awards": [
            "Excellent team worker"
          ],
          "examName": "GMAT",
          "examScore": "730",
          "experience": "4 years",
          "proffessionalDetails": [
            {
              "companyName": "Ugam Soluions",
              "designation": " Product Engineer"
            },
            {
              "companyName": "Infoys Ltd",
              "designation": " Senior Systems Engineer"
            }
          ],
          "recommendations": [
            "The hiring manager wants to know what experiences the candidate will bring to the new role, how she’ll contribute to the company or organization, and how she’ll behave in the day-to-day. Recommendation letters can point to a candidate’s future performance by talking about her past achievements.",
            "test data",
            "test value"
          ],
          "scholarships": [
            "ABC"
          ],
          "socialWork": [
            "NGO",
            "teaching"
          ],
          "universityDetails": [
            {
              "universityName": "harvard",
              "status": "accepted"
            },
            {
              "universityName": "Chicago booth",
              "status": "applied"
            }
          ]
        }
      }
    ]
  }
}


Ex: College Details in ES

{
  "took": 3,
  "timed_out": false,
  "_shards": {
    "total": 3,
    "successful": 3,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 1,
    "hits": [
      {
        "_index": "collegedetails",
        "_type": "collegedetails",
        "_id": "0f44f65698fd138c54ffdf39bb4d2694",
        "_score": 1,
        "_source": {
          "collegeName": "harvard",
          "course": "mba",
          "examName": "gmat",
          "score": 730
        }
      }
    ]
  }
}


Queries written in ES/Kibana


PUT studentdetails
{
    "settings" : {
        "index" : {
            "number_of_shards" : 3,
            "number_of_replicas" : 2
        }
    }
}

PUT collegedetails
{
    "settings" : {
        "index" : {
            "number_of_shards" : 3,
            "number_of_replicas" : 2
        }
    }
}

GET /collegedetails/collegedetails/_search
{
  "query":{
    "match_all":{}
  }
}

GET /studentdetails/studentdetails/_search
{
  "query":{
    "match_all":{}
  }
} 


