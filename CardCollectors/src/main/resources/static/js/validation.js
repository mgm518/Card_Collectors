document.getElementById("signupForm").addEventListener("submit",
    function (event) {
      event.preventDefault(); // Prevent the form from submitting

      // Clear all previous error messages
      document
      .querySelectorAll(".error-message")
      .forEach((el) => (el.style.display = "none"));
      document.querySelectorAll(".input-control").forEach((input) => {
        input.classList.remove("error");
        input.classList.remove("success");
      });

      const name = document.getElementById("name").value.trim();
      const email = document.getElementById("email").value.trim();
      const password = document.getElementById("password").value.trim();

      let valid = true;

      if (!name) {
        valid = false;
        document.getElementById("nameError").style.display = "block";
        document
        .querySelector("#name")
        .closest(".input-control")
        .classList.add("error");
      } else {
        document
        .querySelector("#name")
        .closest(".input-control")
        .classList.add("success");
      }

      if (!validateEmail(email)) {
        valid = false;
        document.getElementById("emailError").style.display = "block";
        document
        .querySelector("#email")
        .closest(".input-control")
        .classList.add("error");
      } else {
        document
        .querySelector("#email")
        .closest(".input-control")
        .classList.add("success");
      }

      if (!validatePassword(password)) {
        valid = false;
        document.getElementById("passwordError").style.display = "block";
        document
        .querySelector("#password")
        .closest(".input-control")
        .classList.add("error");
      } else {
        document
        .querySelector("#password")
        .closest(".input-control")
        .classList.add("success");
      }

      if (valid) {
        alert("Form submitted successfully!");
        this.submit();
      }
    });

function validateEmail(email) {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
}

function validatePassword(password) {
  const re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/;
  return re.test(password);
}
