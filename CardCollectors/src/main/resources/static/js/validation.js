document.getElementById("form").addEventListener("submit", function (event) {
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
  const confirmPassword = document
    .getElementById("confirm-password")
    .value.trim();
  const terms = document.getElementById("terms").checked;

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

  if (!validatePhone(tel)) {
    valid = false;
    document.getElementById("telError").style.display = "block";
    document
      .querySelector("#tel")
      .closest(".input-control")
      .classList.add("error");
  } else {
    document
      .querySelector("#tel")
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

  if (!confirmPassword || !validateConfirmPassword(confirmPassword, password)) {
    valid = false;
    document.getElementById("confirmPasswordError").style.display = "block";
    document
      .querySelector("#confirm-password")
      .closest(".input-control")
      .classList.add("error");
  } else {
    document
      .getElementById("confirm-password")
      .parentNode.classList.add("success");
  }

  if (!refer) {
    valid = false;
    document.getElementById("referError").style.display = "block";
  }

  if (!terms) {
    valid = false;
    document.getElementById("termsError").style.display = "block";
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

function validatePhone(phone) {
  const re = /^\+?[1-9]\d{1,14}$/;
  return re.test(phone);
}

function validatePassword(password) {
  const re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/;
  return re.test(password);
}

function validateConfirmPassword(confirmPassword, password) {
  return password.localeCompare(confirmPassword) == 0;
}
