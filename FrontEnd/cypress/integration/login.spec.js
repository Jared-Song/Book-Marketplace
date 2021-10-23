describe("Login", () => {
  it("should not login without input", () => {
    cy.visit("http://localhost:3000/login")

    cy.get("[id=login-act-btn]").click();
    cy.url().should("include", "/login");
    cy.get("[id=component-error-text]").contains("required field");
  })

  it("should not login as username or password not correct", () => {
    cy.visit("http://localhost:3000/login");
    cy.get('[id="username-input"]').type("test");
    cy.get("[id=password-input]").type("test");
    cy.get("[id=login-act-btn]").click();
    cy.url().should("include", "/login");
  })

  it("should login", () => {
    cy.visit("http://localhost:3000/login");
    cy.get("[id=username-input]").type("user");
    cy.get("[id=password-input]").type("password");
    cy.get("[id=login-act-btn]").click();
    cy.url().should("include", "/account");
  })
})