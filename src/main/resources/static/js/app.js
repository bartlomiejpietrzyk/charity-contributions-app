document.addEventListener("DOMContentLoaded", function () {

    /**
     * Form Select
     */

    class FormSelect {
        constructor($el) {
            this.$el = $el;
            this.options = [...$el.children];
            this.init();
        }

        init() {
            this.createElements();
            this.addEvents();
            this.$el.parentElement.removeChild(this.$el);
        }

        createElements() {
            // Input for value
            this.valueInput = document.createElement("input");
            this.valueInput.type = "text";
            this.valueInput.name = this.$el.name;

            // Dropdown container
            this.dropdown = document.createElement("div");
            this.dropdown.classList.add("dropdown");

            // List container
            this.ul = document.createElement("ul");

            // All list options
            this.options.forEach((el, i) => {
                const li = document.createElement("li");
                li.dataset.value = el.value;
                li.innerText = el.innerText;

                if (i === 0) {
                    // First clickable option
                    this.current = document.createElement("div");
                    this.current.innerText = el.innerText;
                    this.dropdown.appendChild(this.current);
                    this.valueInput.value = el.value;
                    li.classList.add("selected");
                }

                this.ul.appendChild(li);
            });

            this.dropdown.appendChild(this.ul);
            this.dropdown.appendChild(this.valueInput);
            this.$el.parentElement.appendChild(this.dropdown);
        }

        addEvents() {
            this.dropdown.addEventListener("click", e => {
                const target = e.target;
                this.dropdown.classList.toggle("selecting");

                // Save new value only when clicked on li
                if (target.tagName === "LI") {
                    this.valueInput.value = target.dataset.value;
                    this.current.innerText = target.innerText;
                }
            });
        }
    }

    document.querySelectorAll(".form-group--dropdown select").forEach(el => {
        new FormSelect(el);
    });

    /**
     * Hide elements when clicked on document
     */
    document.addEventListener("click", function (e) {
        const target = e.target;
        const tagName = target.tagName;

        if (target.classList.contains("dropdown")) return false;

        if (tagName === "LI" && target.parentElement.parentElement.classList.contains("dropdown")) {
            return false;
        }

        if (tagName === "DIV" && target.parentElement.classList.contains("dropdown")) {
            return false;
        }

        document.querySelectorAll(".form-group--dropdown .dropdown").forEach(el => {
            el.classList.remove("selecting");
        });
    });

    /**
     * Switching between form steps
     */

    class FormSteps {
        constructor(form) {
            this.$form = form;
            this.$next = form.querySelectorAll(".next-step");
            this.$prev = form.querySelectorAll(".prev-step");
            this.$step = form.querySelector(".form--steps-counter span");
            this.currentStep = 1;

            this.$stepInstructions = form.querySelectorAll(".form--steps-instructions p");
            const $stepForms = form.querySelectorAll("form > div");
            this.slides = [...this.$stepInstructions, ...$stepForms];

            /**
             * Input
             */

            this.$numeric = this.$form.querySelectorAll('input.number');
            this.$formatted = this.$form.querySelectorAll('input.formatted');
            this.$checkboxbuttons = this.$form.querySelectorAll('div[data-step="1"] .checkbox');
            this.$bagQuantity = this.$form.querySelector('div[data-step="2"] input');
            this.$radiobuttons = this.$form.querySelectorAll('div[data-step="3"] .checkbox.radio');
            this.$street = this.$form.querySelector('#street');
            this.$city = this.$form.querySelector('#city');
            this.$zipCode = this.$form.querySelector('#zipCode');
            this.$mobile = this.$form.querySelector('#mobile');
            this.$date = this.$form.querySelector('#date');
            this.$time = this.$form.querySelector('#time');
            this.$comment = this.$form.querySelector('#comment');
            this.init();
        }

        /**
         * Init all methods
         */
        init() {
            this.events();
            this.additionalEvents();
            this.updateForm();
        }

        /**
         * All basic events that are happening in form
         */
        events() {
            // Next step
            this.$next.forEach(btn => {
                btn.addEventListener("click", e => {
                    e.preventDefault();
                    if (this.generalValidation()) {
                        this.currentStep++;
                        this.updateForm();
                    }
                });
            });

            // Previous step
            this.$prev.forEach(btn => {
                btn.addEventListener("click", e => {
                    e.preventDefault();
                    this.currentStep--;
                    this.updateForm();
                });
            });

            // Form submit
            this.$form.querySelector("form").addEventListener("submit", e => this.submit(e));
        }

        /**
         * Additional events
         */
        additionalEvents() {
            let invisibleAlertElement = this.$form.querySelector('div.btn.btn--large.wrong');

            if (invisibleAlertElement.querySelector('p.error') != null)
                invisibleAlertElement.classList.remove('wrong');
            //
            this.$form.querySelectorAll('div.btn.btn--large span.error').forEach(element => {
                var element = element.previousElementSibling;
                element.classList.remove('wrong');
            });

            // Number inputs
            this.$numeric.forEach(input => {
                // clear 0
                input.value = '';

                // check if key is numeric
                input.addEventListener('keypress', e => {
                    let key = e.key;
                    if (isNaN(parseInt(key)) && key != '/') {
                        e.preventDefault();
                        $(input).tooltip('show');
                    } else
                        $(input).tooltip('hide');
                });
            });

            // Formatted inputs
            this.$formatted.forEach(input => {
                let format = input.value;


                // set cursor when empty
                input.addEventListener('click', e => {
                    if (input.selectionStart == input.value.length && input.value == format)
                        input.setSelectionRange(0, 0);
                });

                // enter character
                input.addEventListener('keypress', e => {
                    let key = e.key;
                    if (isNaN(parseInt(key))) {
                        e.preventDefault();
                        $(input).tooltip('show');
                        return;
                    } else
                        $(input).tooltip('hide');

                    e.preventDefault();
                    let selectionStart = input.selectionStart;

                    if (selectionStart == format.length)
                        return;

                    let index = setIndex(selectionStart, 1);
                    let value = input.value;
                    input.value = value.replaceCharacter(index, key);
                    input.setSelectionRange(index + 1, index + 1);
                });

                // remove character
                input.addEventListener('keydown', e => {
                    if (e.keyCode != 8)
                        return;

                    let selectionStart = input.selectionStart;
                    if (selectionStart == 0)
                        return;

                    e.preventDefault();

                    let index = setIndex(selectionStart - 1, -1);
                    input.value = input.value.replaceCharacter(index, format.charAt(index));
                    input.setSelectionRange(index, index);
                });


                function setIndex(selectionStart, modification) {
                    switch (format) {
                        case '__-___':
                            if (selectionStart != 2)
                                return selectionStart;
                            else
                                return selectionStart + modification;

                        case 'YYYY-MM-DD':
                            if (selectionStart != 4 && selectionStart != 7)
                                return selectionStart;
                            else
                                return selectionStart + modification;

                        case 'HH:MM':
                            if (selectionStart != 2)
                                return selectionStart;
                            else
                                return selectionStart + modification;
                    }
                }
            });
        }

        /**
         * Validate form
         */

        generalValidation() {
            switch (this.currentStep) {
                case 1:
                    return validateFirst3steps(1, isAnyChecked(this.$form.querySelectorAll('div[data-step="1"] .checkbox')),
                        "Należy wybrać przynajmniej jedną opcję!");

                case 2:
                    this.$bagQuantity.value = this.$bagQuantity.value.replace(/^0+/, '');
                    return validateFirst3steps(2, this.$bagQuantity.value != '', "Minimalna wartość to 1!");
                case 3:
                    return validateFirst3steps(3, isAnyChecked(this.$radiobuttons), "Należy wybrać jedną fundację z listy!");
                case 4:
                    return validateFourthStep(this);
            }

            function isAnyChecked(boxes) {
                for (let i = 0; i < boxes.length; i++) {
                    if (boxes[i].parentElement.querySelector('input').checked === true)
                        return true;
                }
                return false;
            }

            function validateFirst3steps(step, isCorrect, errorInfo) {
                let actualForm = document.querySelector('div[data-step="' + step + '"]');
                let errorDiv = actualForm.querySelector('div.btn.btn--large');

                if (isCorrect) {
                    if (errorDiv != null)
                        actualForm.removeChild(errorDiv);
                    return isCorrect;
                } else {
                    if (errorDiv != null)
                        return isCorrect;
                }

                createAlert(step, errorInfo);
                return isCorrect;
            }

            function validateFourthStep(form) {
                function choosenDate(dateArray) {
                    let currentDate = new Date();
                    let currentYear = currentDate.getFullYear();
                    let currentMonth = currentDate.getMonth() + 1;
                    let currentDay = currentDate.getDay();

                    return parseInt(dateArray[0]) >= currentYear &&
                        parseInt(dateArray[1]) >= currentMonth &&
                        parseInt(dateArray[2]) >= currentDay;
                }

                function correctDate(dateArray) {
                    let maxDay;
                    switch (dateArray[1]) {
                        case 2:
                            if (dateArray[0] % 4 != 0)
                                maxDay = 28;
                            else
                                maxDay = 29;
                            break;
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            maxDay = 31;
                            break;
                        default:
                            maxDay = 30;

                    }

                    return /^\d{4}-\d{2}-\d{2}$/.test(form.$date.value) &&
                        parseInt(dateArray[1]) > 0 && parseInt(dateArray[1]) <= 12 &&
                        parseInt(dateArray[2]) > 0 && parseInt(dateArray[1]) <= maxDay;
                }

                function correctTime(timeArray) {
                    return /^\d{2}:\d{2}$/.test(form.$time.value) &&
                        parseInt(timeArray[0]) >= 0 && parseInt(timeArray[0]) <= 23 &&
                        parseInt(timeArray[1]) >= 0 && parseInt(timeArray[1]) <= 59;
                }

                function choosenTime(timeArray) {
                    let currentDate = new Date();
                    let currentHour = currentDate.getHours();
                    let currentMinutes = currentDate.getMinutes();

                    let currentYear = currentDate.getFullYear();
                    let currentMonth = String(currentDate.getMonth() + 1).padStart(2, '0');
                    let currentDay = String(currentDate.getDate()).padStart(2, '0');

                    if (form.$date.value != currentYear + '-' + currentMonth + '-' + currentDay)
                        return true;

                    return parseInt(timeArray[0]) >= currentHour &&
                        parseInt(timeArray[1]) >= currentMinutes;
                }


                let information = '';
                let correct = true;

                if (form.$street.toString() === '') {
                    correct = false;
                    information = information.concat('\n').concat('Nazwa ulicy nie może być pusta.');
                }
                if (form.$city.value == '') {
                    correct = false;
                    information = information.concat('\n').concat('Nazwa miasta nie może być pusta.');
                }
                if (form.$zipCode.value == '') {
                    correct = false;
                    information = information.concat('\n').concat('Kod pocztowy nie może być pusty.');
                }
                if (!/^\d{2}-\d{3}$/.test(form.$zipCode.value)) {
                    correct = false;
                    information = information.concat('\n').concat('Podano nieprawidłowy kod pocztowy.');
                }
                if (form.$mobile.value.length < 7) {
                    correct = false;
                    information = information.concat('\n').concat('Podano nieprawidłowy numer.');
                }
                let dateArray = form.$date.value.split('-');
                if (!correctDate(dateArray)) {
                    correct = false;
                    information = information.concat('\n').concat('Podano nieprawidłową datę.');
                } else if (!choosenDate(dateArray)) {
                    correct = false;
                    information = information.concat('\n').concat('Podano datę wcześniejszą niż dzisiejsza.');
                }
                let timeArray = form.$time.value.split(':');
                if (!correctTime(timeArray)) {
                    correct = false;
                    information = information.concat('\n').concat('Podano nieprawidłową godzinę.');
                } else if (!choosenTime(timeArray)) {
                    correct = false;
                    information = information.concat('\n').concat('Podano godzinę, która już minęła.');
                }

                let alert = document.querySelector('div[data-step="4"] div.btn');
                if (alert != null)
                    alert.parentElement.removeChild(alert);

                if (!correct) {
                    information = information.replace(/\n/, '');
                    createAlert(4, information);
                }

                return correct;
            }

            /**
             * Alert div configuration
             */

            function createAlert(step, errorInfo) {
                let actualForm = document.querySelector('div[data-step="' + step + '"]');
                let errorDiv = document.createElement("div");

                errorDiv.innerText = errorInfo;
                errorDiv.classList.add("form-group");
                errorDiv.classList.add("btn");
                errorDiv.classList.add("span.error");

                actualForm.insertBefore(errorDiv, actualForm.querySelector('div'));
            }
        }

        /**
         * Update form front-end
         * Show next or previous section etc.
         */
        updateForm() {
            this.$step.innerText = this.currentStep;

            this.slides.forEach(slide => {
                slide.classList.remove("active");

                if (slide.dataset.step == this.currentStep) {
                    slide.classList.add("active");
                }
            });

            this.$stepInstructions[0].parentElement.parentElement.hidden = this.currentStep >= 5;
            this.$step.parentElement.hidden = this.currentStep >= 5;


            this.$form.querySelector('#categoryProceed').innerText = this.$checkboxbuttons.value;
            this.$form.querySelector('#quantityProceed').innerText = getBagsQuantity(this.$bagQuantity.value);
            this.$form.querySelector('#institutionProceed').innerText = getInstitutionDonate(this.$radiobuttons);
            this.$form.querySelector('#streetProceed').innerText = this.$street.value;
            this.$form.querySelector('#cityProceed').innerText = this.$city.value;
            this.$form.querySelector('#zipCodeProceed').innerText = this.$zipCode.value;
            this.$form.querySelector('#mobileProceed').innerText = getMobileNumber(this.$mobile.value);
            this.$form.querySelector('#dateProceed').innerText = this.$date.value;
            this.$form.querySelector('#timeProceed').innerText = this.$time.value;
            this.$form.querySelector('#commentProceed').innerText = getCommentValue(this.$comment.value);

            function getBagsQuantity(quantity) {
                if (parseInt(quantity) === 1)
                    return quantity + ' worek';
                else if (quantity.charAt(quantity.length - 1) == '1' ||
                    (parseInt(quantity.substr(quantity.length - 2)) >= 5
                        && parseInt(quantity.substr(quantity.length - 2)) <= 20))
                    return quantity + ' worków';
                else if (parseInt(quantity.charAt(quantity.length - 1)) >= 2 && parseInt(quantity.charAt(quantity.length - 1)) <= 4)
                    return quantity + ' worki';
            }

            function getInstitutionDonate(boxes) {
                for (let i = 0; i < boxes.length; i++) {
                    if (boxes[i].parentElement.querySelector('input').checked === true)
                        return boxes[i].parentElement.querySelector('span.description div.title span#institution-name').innerText;
                }
            }

            function getMobileNumber(mobile) {
                if (mobile.length == 9)
                    return mobile.substr(0, 3) + ' ' + mobile.substr(3, 3) + ' ' + mobile.substr(6, 3)
                else
                    return mobile;
            }

            function getCommentValue(comment) {
                if (comment != '')
                    return comment;
                else
                    return 'Brak uwag';
            }
        }

    }

    const form = document.querySelector(".form--steps");
    if (form !== null) {
        new FormSteps(form);
    }

    String.prototype.replaceCharacter = function (index, replacement) {
        return this.substr(0, index).concat(replacement).concat(this.substr(index + replacement.length));
    };
});
