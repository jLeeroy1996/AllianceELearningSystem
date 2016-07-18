<%@page import="com.aes.model.Course"%>
<%@page import="java.util.List"%>
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
    if (user == null || user.getUserType().getUserType().equals("trainee")){
        response.sendRedirect("redirect.jsp");
    } else {
        String type = user.getUserType().getUserType().toString();
    }
%>


<html>
    <head>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon/alliance.ico"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/exam-generator.css">
        <link rel="icon" href="favicon.ico"/>
        <title>Edit Exam</title>
    </head>
    <body>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/react.js"></script>
        <script>
        var ExamEditor = React.createClass({displayName: "ExamEditor",        
	buildNewQuestion: function (index) {
            return {
                'questionText': '',
                'choices': [
                        this.buildNewChoice(1),
                        this.buildNewChoice(2)
                ],
                'correctAnswers': -1,
                'correctType': 'and',
                'index' : index
            };
	},
	buildNewChoice: function (id) {
            return {
                'id': id,
                'choiceText': '',
            };
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
                        this.loadExam(exam, response.examId, response.passingRate);
                    } else {
                        alert (response.error);
                    }                                    
                }.bind(this),
                error: function (xhr, status, err) {
                        console.error(this.props.url, status, err.toString());
                }.bind(this)
            });
        },
	getInitialState: function () {
            return {
                'title': '',
                'course': -1,
                'description': '',
                'hours': 1,
                'minutes': 30,
                'allotedTime': 90,
                'passingRate': 50,
                'questions': [this.buildNewQuestion(1)]
            };
	},
	addQuestion: function () {
            this.setState({
                'questions': this.state.questions.concat([this.buildNewQuestion(this.state.questions.length + 1)])
            });
	},
        loadExam: function (exam, id, passingRate) {
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
                course: exam.course,
                scoreId: 0
            });
            
            this.state.questions.forEach(function(question, index) {
                if (question.correctAnswers !== -1) {
                    var choiceNodes = document.getElementsByName('choice' + index);

                    if (question.correctAnswers.constructor === Array) {
                        for (var i = 0; i < choiceNodes.length; i++) {
                            if (question.correctAnswers.indexOf('' + choiceNodes[i].dataset.choiceid) !== -1) {
                                    choiceNodes[i].checked = true;
                            }
                        }
                    } else {
                        for (var i = 0; i < choiceNodes.length; i++) {
                            if (choiceNodes[i].dataset.choiceid == question.correctAnswers) {
                                    choiceNodes[i].checked = true;
                            }
                        }
                    }
                }
            });
        },
	onAddChoice: function (questionIndex) {
            var questions = this.state.questions;
            questions[questionIndex].choices.push(this.buildNewChoice(questions[questionIndex].choices.length + 1));
            this.setState({'questions': questions});
	},
	onToggleChoice: function (questionIndex, choiceIndex) {
            var questions = this.state.questions;
            var choicesNodes = document.getElementsByName('choice' + questionIndex);
            var checkedChoices = [];
            for (var i = 0; i < choicesNodes.length; i++) {
                if (choicesNodes[i].checked) {
                    checkedChoices.push(choicesNodes[i].dataset.choiceid);
                }
            }

            if (checkedChoices.length == 1) {
                questions[questionIndex].correctAnswers = checkedChoices[0];
            } else {
                questions[questionIndex].correctAnswers = checkedChoices;
            }
            this.setState({'questions': questions});
	},
	onChoiceTextChanged: function (questionIndex, choiceIndex, newValue) {
            var questions = this.state.questions;
            questions[questionIndex].choices[choiceIndex].choiceText = newValue;
            this.setState({'questions': questions});
	},
	onQuestionTextChanged: function (questionIndex, newValue) {
            var questions = this.state.questions;
            questions[questionIndex].questionText = newValue;
            this.setState({'questions': questions});
	},
	onRemoveQuestion: function (questionIndex) {
            if (this.state.questions.length > 1) {
                var questions = this.state.questions;
                questions.splice(questionIndex, 1);
                this.setState({questions: questions});
            }
	},
	onRemoveChoice: function (questionIndex, choiceIndex) {
            var question = this.state.questions[questionIndex];

            if (question.choices.length > 2) {
                question.choices.splice(choiceIndex, 1);
                var questions = this.state.questions;
                questions[questionIndex] = question;
                this.setState({questions: questions});
            }
	},
	onCorrectTypeChange: function (questionIndex, correctType) {
		var questions = this.state.questions;
		var question = questions[questionIndex];
		
		question.correctType = correctType;
		this.setState({
			questions: questions
		});
	},
	handleTitleChange: function (e) {
            this.setState({'title': e.target.value});
	},
        handlePassingRateChange: function (e) {
            this.setState({'passingRate': e.target.value});
	},
	handleDescriptionChange: function (e) {
            this.setState({'description': e.target.value});
	},
	handleHourChange: function (e) {
            var temp = parseInt(e.target.value);

            this.setState({
                'hours': temp,
                'allotedTime': this.state.minutes + (temp * 60)
            });
	},
	handleMinuteChange: function (e) {
            var temp = parseInt(e.target.value);

            this.setState({
                'minutes': temp,
                'allotedTime': temp + (this.state.hours * 60)
            });
	},
	jsonifyExam: function () {
            var errors = '';
            var flag = true;

            if (this.state.title === ''){
                errors += 'Title is empty.\n';
            }
            if (this.state.description === ''){
                errors += 'Description is empty.\n';
            }
            if (this.state.passingRate < 1 || this.state.passingRate > 99){
                errors += 'Please enter a valid passing rate. (1-99%) \n';
            }                
            if (this.state.course === ''){
                errors += 'Course is not selected.\n';
            }

            if (this.state.hours == 0 && this.state.minutes == 0) {
                errors += 'Please enter a valid exam duration. Minimum should be 1 minute.\n';
            }

            if (isNaN(this.state.hours) || isNaN(this.state.minutes)){
                errors += 'Please enter a valid exam duration.\n';
            }

            this.state.questions.forEach(function(question, i) {
                if (question.questionText === '') {
                    errors += 'Question #' + (i + 1) + ' is empty.\n';
                } else {
                    question.choices.forEach(function(choice, j) {
                        if (choice.choiceText === '') {
                            errors += 'Question #' + (i + 1) + ' choice #' + (j + 1) + ' is empty.\n';
                            flag = false;
                        }
                    });
                    if (question.correctAnswers.length < 1 || (typeof question.correctAnswers.length === 'undefined')) {
                        errors += 'Question #' + (i + 1) + ' has no correct answer(s).\n'
                    } else if (flag &&
                        question.correctAnswers.constructor === Array &&
                        question.correctAnswers.length === question.choices.length) {
                        errors += 'All of the choices in question #' + (i + 1) + ' are correct.\n'
                    }
                }
                flag = true;
            });

            if (errors === '') {

                var examData = {
                    "id": this.state.examId.toString(),
                    "description": this.state.description,
                    "exam" : JSON.stringify(this.state),
                    "course": this.state.course.toString(),
                    "title": this.state.title,
                    "passingRate": this.state.passingRate.toString()
                };

                $.ajax({
                    url:'put/exam',
                    type:'POST',
                    data: JSON.stringify(examData),
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',                            
                    success:function(res){
                        if (res.error == ""){
                            alert("Exam successfully updated.");
                            window.location.href = "${user}#/course_details?id=" + examData.course;
                        } else {
                            alert (res.error);
                        }
                    },
                    error:function(res){
                        alert("Ajax error. " + res.statusText);
                    }
                });   

            } else {
                alert(errors);
            }
	},
	render: function () {

            var questions = this.state.questions.map(function (question, index) {
                return (
                    React.createElement(Question, {
                        key: index,
                        index: index,
                        src: question,
                        onRemoveQuestion: this.onRemoveQuestion,
                        onRemoveChoice: this.onRemoveChoice,
                        onQuestionTextChanged: this.onQuestionTextChanged,
                        onChoiceTextChanged: this.onChoiceTextChanged,
                        onToggleChoice: this.onToggleChoice,
                        onCorrectTypeChange: this.onCorrectTypeChange,
                        onAddChoice: this.onAddChoice})
                );
            }, this);
            var courses = [];

            return (
                React.createElement("div", {className: "exam form-group"},
                    React.createElement("h1", null, "Exam Editor"),
                    React.createElement("div", {className: "exam-header"},
                        React.createElement("span", null, "Exam Title"),
                        React.createElement("input", {type: "text", value: this.state.title, onChange: this.handleTitleChange})
                    ),
                    React.createElement("div", {className: "exam-header"},
                        React.createElement("span", null, "Alloted Time"),
                        React.createElement("div", {className: "exam-header-time"},
                            React.createElement("input", {type: "number", min: 0, value: this.state.hours, onChange: this.handleHourChange}),
                            React.createElement("span", null, "hours"),
                            React.createElement("input", {type: "number", min: 1, value: this.state.minutes, onChange: this.handleMinuteChange}),
                            React.createElement("span", null, "minutes")
                        )
                    ),
                    React.createElement("div", {className: "exam-header"},
                        React.createElement("span", null, "Test Description"),
                        React.createElement("div", {className: "exam-header"},
                            React.createElement("textarea", {value: this.state.description, cols: 50, onChange: this.handleDescriptionChange})
                        )
                    ),                    
                    React.createElement("div", {className: "exam-header"},
                        React.createElement("span", null, "Passing Rate (1-99)%"),
                        React.createElement("div", {className: "exam-header"},
                            React.createElement("input", {type: "number", min: 1, max: 99, value: this.state.passingRate, onChange: this.handlePassingRateChange})
                        )
                    ),
                    React.createElement("div", {className: "exam-header"},
                        React.createElement("span", null, "Course"),
                        React.createElement("div", {className: "exam-header"},
                            React.createElement("select", {value: this.state.course, onChange: this.handleCourseChange}, 
                            React.createElement("option", {value: "${course.id}", disabled: "true", selected: "selected"}, "Select Course")
                                <c:forEach items="${courses}" var="course">
                                    , React.createElement("option", {value: "${course.id}"}, "${course.courseTitle}")
                                </c:forEach>
                            )
                        )
                    ),
            
                    React.createElement("div",{className: "pull-right"}, 
                    React.createElement("a", {href: "${user}#/manage_exam", className:"pull-right"}, "Exams List")),
                    React.createElement("div", {className: "exam-questions"},
                        questions
                    ),
                    React.createElement("button", {onClick: this.addQuestion}, "Add a Question"),
                    React.createElement("button", {onClick: this.jsonifyExam}, "Finish Exam"),
                    React.createElement("div",{className: "pull-right"}, 
                    React.createElement("a", {href: "${user}#/manage_courses", className:"pull-right"}, "Courses List"))
                )
            );
	}
    });    

    var Question = React.createClass({displayName: "Question ",
            removeQuestion: function () {
                this.props.onRemoveQuestion(this.props.index);
            },
            removeChoice: function (choiceIndex) {
                this.props.onRemoveChoice(this.props.index, choiceIndex);
            },
            addChoice: function () {
                this.props.onAddChoice(this.props.index);
            },
            handleTextChange: function (e) {
                this.props.onQuestionTextChanged(this.props.index, e.target.value);
            },
            handleToggleChoice: function (choiceIndex) {
                this.props.onToggleChoice(this.props.index, choiceIndex);
            },
            handleChoiceTextChange: function (choiceIndex, newValue) {
                this.props.onChoiceTextChanged(this.props.index, choiceIndex, newValue);
            },
            handleCorrectTypeChange: function (e) {
                this.props.onCorrectTypeChange(this.props.index, e.target.value);
            },
            render: function () {
                var choices = this.props.src.choices.map(function (choice, index) {
                    return (
                        React.createElement(Choice, {
                            key: index,
                            index: index,
                            src: choice,
                            group: this.props.index,
                            onRemoveChoice: this.removeChoice,
                            onToggleChoice: this.handleToggleChoice,
                            onChoiceTextChanged: this.handleChoiceTextChange})
                    );
                }, this);
                
                if (this.props.src.correctAnswers.constructor === Array &&
                    this.props.src.correctAnswers.length) {
                    var options = (
                        React.createElement("select", {onChange: this.handleCorrectTypeChange, defaultValue: this.props.src.correctType}, 
                            React.createElement("option", {value: "and"}, " Answer should include all choices"), 
                            React.createElement("option", {value: "or"}, " At least one choice should be correct")
                        )
                    );
                }

                return (
                    React.createElement("div", {className: "exam-question"},
                        React.createElement("span", null, React.createElement("button", {className: "btn btn-xs btn-primary", onClick: this.removeQuestion}, "x"), " Question ", this.props.src.index.toString() + '   ', options),
                        React.createElement("textarea", {value: this.props.src.questionText, onChange: this.handleTextChange}),
                        React.createElement("div", {className: "exam-question-choices"},
                                choices,
                            React.createElement("button", {onClick: this.addChoice}, "Add a Choice")
                        )
                    )
                );
            }
    });

    var Choice = React.createClass({displayName: "Choice",
            removeChoice: function () {
                this.props.onRemoveChoice(this.props.index);
            },
            handleTextChange: function (e) {
                this.props.onChoiceTextChanged(this.props.index, e.target.value);
            },
            toggleChoice: function () {
                this.props.onToggleChoice(this.props.index);
            },
            render: function () {
                return (
                    React.createElement("div", null,
                        React.createElement("button", {onClick: this.removeChoice}, "x"),
                        React.createElement("input", {type: "text", value: this.props.src.choiceText, onChange: this.handleTextChange}),
                        React.createElement("input", {type: "checkbox", name: 'choice' + this.props.group, "data-choiceid": this.props.src.id, onChange: this.toggleChoice}),
                        React.createElement("span", null, "Correct Answer")
                    )
                );
            }
    });

    React.render(React.createElement(ExamEditor, {url: "get/exam/${examId}"}), document.body);
    </script>

</body>
</html>

