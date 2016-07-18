<%@page import="com.aes.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    response.setHeader("Cache-Control","no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires", -1);
    
    User user = (User)session.getAttribute("loggedUser");
    if (user == null){
        response.sendRedirect("redirect.jsp");
    }       
%>


<html>
    <head>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon/alliance.ico"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/exam-player.css">
        <link rel="icon" href="favicon.ico"/>
        <title>Take Exam</title>
    </head>
    <body>  
        
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/react.js"></script>	
        <script>
                var QUESTIONS_PER_PAGE = 5;
                var SCORE_UPDATE_INTERVAL = 30; // 1 = 1 second
                var SCORE_ID = -1;
                
                var ExamPlayer = React.createClass({displayName: "ExamPlayer",
                getInitialState: function () {
                    return {
                        status: 'loading',
                        title: 'Loading...',
                        hours: 'Loading...',
                        minutes: 'Loading...',
                        allotedTime: 'Loading...',
                        description: 'Loading...',
                        questions: [],
                        passingRate: 0,
                        answers: [],
                        currentPage: 1,
                        scoreId: -1
                    };
                },
                formatRemainingTime: function () {
                    var remainingTimeInSeconds = this.state.allotedTime;
                    /* it is safe to assume that remainingTimeInSeconds is above 0 */
                    var h = parseInt(remainingTimeInSeconds / 3600);
                    var m = parseInt(remainingTimeInSeconds % 3600 / 60);
                    var s = remainingTimeInSeconds % 3600 % 60;

                    return h + " hours " + m + " minutes " + s + " seconds remaining.";
                },
                reInitializeExam: function () {
                    this.setState({
                        allotedTime: (this.state.hours * 60 + this.state.minutes) * 60,
                        status: 'ready',
                        answers: []
                    });
                },
                initializeExam: function (exam, id, passingRate) {
                    this.setState({
                        title: exam.title,
                        description: exam.description,
                        passingRate: passingRate,
                        hours: exam.hours,
                        minutes: exam.minutes,
                        allotedTime: exam.allotedTime * 60,                        
                        questions: exam.questions,
                        examId: id,
                        status: 'ready',
                        scoreId: 0
                    });
                    this.reInitializeExam();                    
                },
                tick: function () {
                    var time = this.state.allotedTime-1;
                    
                    if (time % SCORE_UPDATE_INTERVAL === 0){
                        var scoreData = {
                            "score": this.checkAnswers().toString(),
                            "scoreId": SCORE_ID.toString()
                        };
                        
                        $.ajax({
                            url:'put/score',
                            type:'POST',
                            data: JSON.stringify(scoreData),
                            contentType: "application/json; charset=utf-8",
                            dataType: 'json',                            
                            success:function(res){
                            },
                            error:function(res){
                                alert("Ajax error. " + res.statusText);
                            }
                        });
                    }

                    if (time > 0) {
                        this.setState({
                            allotedTime: time
                        });
                    } else {
                        this.endExamTimedOut();
                    }
                },
                startExam: function () {
                    this.setState({
                        status: 'running',
                    });
                    this.interval = setInterval(this.tick, 1000);
                    var scoreData = {
                        "score": this.checkAnswers().toString(),
                        "maxScore": this.state.questions.length.toString(),
                        "passingRate": this.state.passingRate.toString(),
                        "examId": this.state.examId.toString()
                    };   
                    $.ajax({
                        url:'post/score',
                        type:'POST',
                        data: JSON.stringify(scoreData),
                        contentType: "application/json; charset=utf-8",
                        dataType: 'json',                            
                        success:function(res){
                            if (res.error == ""){
                                SCORE_ID = res.scoreId;
                            } else {
                                alert (res.error);
                            }
                        },
                        error:function(res){
                            alert("Ajax error. " + res.statusText);
                        }
                    });
                },
                endExam: function () {  
                        alert('You got ' + this.checkAnswers() + ' out of ' + this.state.questions.length);                                                
                        
                        var scoreData = {
                            "score": this.checkAnswers().toString(),
                            "scoreId": SCORE_ID.toString()
                        };                                                
                        
                        $.ajax({
                            url:'put/score',
                            type:'POST',
                            data: JSON.stringify(scoreData),
                            contentType: "application/json; charset=utf-8",
                            dataType: 'json',                            
                            success:function(res){
                                if (res.error == ""){
                                    window.location.href = "trainee#/past_exams";           
                                } else {
                                    alert (res.error);
                                }
                            },
                            error:function(res){
                                alert("Ajax error. " + res.statusText);
                            }
                        });    
                },
                endExamTimedOut: function () {                     
                    alert('You got ' + this.checkAnswers() + ' out of ' + this.state.questions.length);                                                
                    var scoreData = {
                        "score": this.checkAnswers().toString(),
                        "scoreId": SCORE_ID.toString()
                    };

                    $.ajax({
                        url:'put/score',
                        type:'POST',
                        data: JSON.stringify(scoreData),
                        contentType: "application/json; charset=utf-8",
                        dataType: 'json',                            
                        success:function(res){
                            if (res.error == ""){
                                window.location.href = "trainee#/past_exams";
                            } else {
                                alert (res.error);
                            }
                        },
                        error:function(res){
                            alert("Ajax error. " + res.statusText);
                        }
                    });
                },
                isArraySubset: function (correctAnswers, userAnswers){
                    var result = (userAnswers.length === _.intersection(userAnswers, correctAnswers).length);
                },
                checkAnswers: function () {
                    var correctAnswers = 0;

                    this.state.questions.forEach(function (question, index) {
                        if (typeof this.state.answers[index] === 'undefined') {
                            return;
                        }
                        if (question.correctAnswers.constructor === Array) {
                            // and multiple answers
                            if (question.correctType === 'and') {
                                var flag  = true;
                                if (this.state.answers[index].length == question.correctAnswers.length){
                                    for (var i = 0; i < this.state.answers[index].length; i++){
                                        if (question.correctAnswers.indexOf(this.state.answers[index][i]) == -1){
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if (flag == true){
                                        correctAnswers++;
                                    }
                                }
                            } 
                            // or multiple answers
                            else if (question.correctType === 'or') {
                                var flag  = true;
                                if (this.state.answers[index].length <= question.correctAnswers.length){
                                    for (var i = 0; i < this.state.answers[index].length; i++){
                                        if (question.correctAnswers.indexOf(this.state.answers[index][i]) == -1){
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if (flag == true){
                                        correctAnswers++;
                                    }
                                }
                            }
                        }
                        // for single-answer questions
                        else if (question.correctAnswers == this.state.answers[index]) {
                                console.log("Answers Type: Single");
                                correctAnswers++;
                        }
                    }, this);

                    return correctAnswers;
                },
                submitExam: function () {
                    this.endExam();
                },
                componentDidMount: function () {
                    $.ajax({
                        url: this.props.url,
                        dataType: 'json',
                        cache: false,
                        success: function (response) {
                            if (response.error == ""){
                                var exam = jQuery.parseJSON(response.exam); 
                                exam = JSON.parse(exam);
                                this.initializeExam(exam, response.examId, response.passingRate);                                
                            } else {
                                alert (response.error);
                            }                                    
                        }.bind(this),
                        error: function (xhr, status, err) {
                                console.error(this.props.url, status, err.toString());
                        }.bind(this)
                    });
                },
                handleChangeSingle: function (questionIndex, parsedChoiceId) {
                    var answers = this.state.answers;
                    answers[questionIndex] = parsedChoiceId;
                    this.setState({answers: answers});
                },
                handleScoreIdChange: function (id) {
                    this.setState({scoreId: id});
                },
                handleChangeMultiple: function (questionIndex, parsedChoiceId) {
                    var answers = this.state.answers;

                    if (answers[questionIndex]) {
                        var index = $.inArray(parsedChoiceId, answers[questionIndex]);
                        if (index === -1) {
                                answers[questionIndex].push(parsedChoiceId);
                        } else {
                                answers[questionIndex].splice(index, 1);
                        }
                    } else {
                        answers[questionIndex] = [parsedChoiceId];
                    }
                    this.setState({answers: answers});
                },
                gotoNext: function () {
                    this.setState({ currentPage: this.state.currentPage + 1 });
                },
                gotoPrev: function () {
                    this.setState({ currentPage: this.state.currentPage - 1 });
                },
                render: function () {                    
                    if (this.state.status === 'running') {
			if (this.state.questions.length) {
                            console.log('Displaying questions for page ' + this.state.currentPage);
                            var index = (this.state.currentPage - 1) * QUESTIONS_PER_PAGE;
                            console.log('Starting index: ' + index);
                            var endIndex = index + QUESTIONS_PER_PAGE;
                            console.log('Ending index: ' + endIndex);
                            var questions = [];

                            for (; index < endIndex; index++) {
                                var question = this.state.questions[index];

                                // exit immediately if the last question was already rendered
                                if (typeof question === 'undefined') {
                                    break;
                                }

                                // if question was already answered
                                if (this.state.answers[index]) {
                                    questions.push(
                                        React.createElement(Question, {
                                            key: index, 
                                            index: index, 
                                            handleChangeMultiple: this.handleChangeMultiple, 
                                            handleChangeSingle: this.handleChangeSingle, 
                                            answers: this.state.answers[index], 
                                            src: question})
                                    );
                                } else {
                                    questions.push(
                                        React.createElement(Question, {
                                            key: index, 
                                            index: index, 
                                            handleChangeMultiple: this.handleChangeMultiple, 
                                            handleChangeSingle: this.handleChangeSingle, 
                                            src: question})
                                    );
                                }
                            }
			} else {
                            var questions = React.createElement("p", null, "Loading questions ...");
			}

			if (this.state.currentPage * QUESTIONS_PER_PAGE <= this.state.questions.length) {
                            var btnNext = (
                                React.createElement("button", {ref: "next", onClick: this.gotoNext}, "Next")
                            );
			}

			if (this.state.currentPage !== 1) {				
                            var btnPrevious = (
                                React.createElement("button", {ref: "prev", onClick: this.gotoPrev}, "Previous")
                            );
			}

			return (
                            React.createElement("div", {className: "exam"}, 
                                React.createElement("h1", null, "Exam Player"), 
                                React.createElement("p", null, "Exam Title : ", this.state.title),                                 
                                React.createElement("p", null, "Remaining Time : ", this.formatRemainingTime()), 
                                questions, 
                                btnPrevious, 
                                btnNext, 
                                React.createElement("button", {onClick: this.submitExam}, "Submit Exam")
                            )
			);
                    } else {
                        var passingScore = (this.state.questions.length*(this.state.passingRate/100));
                        return (
                            React.createElement("div", {className: "exam"}, 
                                React.createElement("h1", null, "Exam Player"), 
                                React.createElement("p", null, "Exam Title : ", this.state.title), 
                                React.createElement("p", null, "Description : ", this.state.description),
                                React.createElement("p", null, "Max Score : ", this.state.questions.length),
                                React.createElement("p", null, "Passing Rate : ", this.state.passingRate.toString() + "% / " + passingScore.toString() + " points"),
                                React.createElement("p", null, "Alloted Time : ", this.state.hours, " hours and ", this.state.minutes, " minutes"), 
                                React.createElement("br", null), 
                                React.createElement("button", {onClick: this.startExam}, "Start Exam")
                            )
                        );
                    }
                    
                    /*
                    if (this.state.status === 'running') {
                        if (this.state.questions.length) {
                            var questions = this.state.questions.map(function (question, index) {
                                return (
                                    React.createElement(Question, {
                                        key: index, 
                                        index: index, 
                                        handleChangeMultiple: this.handleChangeMultiple, 
                                        handleChangeSingle: this.handleChangeSingle, 
                                        src: question})
                                );
                            }, this);
                        } else {
                            var questions = React.createElement("p", null, "Loading questions ...");
                        }

                        return (
                            React.createElement("div", {className: "exam"}, 
                                React.createElement("h1", null, "Exam Player"), 
                                React.createElement("p", null, "Exam Title : ", this.state.title), 
                                React.createElement("p", null, "Remaining Time : ", this.formatRemainingTime()), 
                                questions, 
                                React.createElement("button", {onClick: this.submitExam}, "Submit Exam")
                            )
                        );
                    } else {
                        return (
                            React.createElement("div", {className: "exam"}, 
                                React.createElement("h1", null, "Exam Title : ", this.state.title), 
                                React.createElement("p", null, "Description : ", this.state.description), 
                                React.createElement("p", null, "Max Score : ", this.state.questions.length),
                                React.createElement("p", null, "Exam Duration : ", this.state.hours, " hours and ", this.state.minutes, " minutes"), 
                                React.createElement("br", null), 
                                React.createElement("button", {onClick: this.startExam}, "Start Exam")
                            )
                        );
                    }*/
                }
        });

        var Question = React.createClass({displayName: "Question",
            handleChange: function (e) {
                if (this.props.src.correctAnswers.constructor === Array) {
                    this.props.handleChangeMultiple(this.props.index, e.target.value);
                } else {
                    this.props.handleChangeSingle(this.props.index, e.target.value);
                }
            },
            render: function () {
                
                if (this.props.src.correctAnswers.constructor === Array) {
                    var choices = this.props.src.choices.map(function (choice, index) {
                        if (typeof this.props.answers !== 'undefined') {
                            console.log(typeof this.props.answers);
                            var defaultChecked = $.inArray('' + choice.id, this.props.answers) !== -1;
                            console.log('Answers: ' + JSON.stringify(this.props.answers));
                            console.log('Choice: ' + choice.id);
                            console.log('Result: ' + defaultChecked);
                        }

                        return (
                            React.createElement("div", {key: choice.id}, 
                                    React.createElement("input", {type: "checkbox", name: 'q' + this.props.index, value: choice.id, onChange: this.handleChange, defaultChecked: defaultChecked}), 
                                    choice.choiceText, 
                                    React.createElement("br", null)
                            )
                        );
                    }, this);
		} else {
                    var choices = this.props.src.choices.map(function (choice, index) {
                        if (typeof this.props.answers !== 'undefined') {
                            var defaultChecked = this.props.answers == choice.id;
                        }

                        return (
                            React.createElement("div", {key: choice.id}, 
                                    React.createElement("input", {type: "radio", name: 'q' + this.props.index, value: choice.id, onChange: this.handleChange, defaultChecked: defaultChecked}), 
                                    choice.choiceText, 
                                    React.createElement("br", null)
                            )
                        );
                    }, this);
		}

		return (
                    React.createElement("div", null, 
                        React.createElement("p", null, 
                            "Q", this.props.index + 1, " : ", this.props.src.questionText, 
                            React.createElement("br", null), 
                            choices
                        )
                    )
		);
                
                
                /*
                if (this.props.src.correctAnswers.constructor === Array) {
                    var choices = this.props.src.choices.map(function (choice, index) {
                        return (
                            React.createElement("div", {key: choice.id}, 
                                React.createElement("input", {type: "checkbox", name: 'q' + this.props.index, value: choice.id, onChange: this.handleChange}), 
                                choice.choiceText, 
                                React.createElement("br", null)
                            )
                        );
                    }, this);
                } else {
                    var choices = this.props.src.choices.map(function (choice, index) {
                        return (
                            React.createElement("div", {key: choice.id}, 
                                React.createElement("input", {type: "radio", name: 'q' + this.props.index, value: choice.id, onChange: this.handleChange}), 
                                choice.choiceText, 
                                React.createElement("br", null)
                            )
                        );
                    }, this);
                }

                return (
                    React.createElement("div", null, 
                        React.createElement("p", null, 
                            "Q", this.props.index + 1, " : ", this.props.src.questionText, 
                            React.createElement("br", null), 
                            choices
                        )
                    )
                );*/
            }
        });
        
        React.render(React.createElement(ExamPlayer, {url: "get/exam/${examId}"}), document.body);
        </script>

</body>
</html>