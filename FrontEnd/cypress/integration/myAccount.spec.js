describe("My Account", () => {
  it("should edit user", () => {
    cy.visit("http://localhost:3000/login");
    cy.get("[id=username-input]").type("user");
    cy.get("[id=password-input]").type("password");
    cy.get("[id=login-act-btn]").click();
    cy.url().should("include", "/account");

    cy.get("[data-test-id=full-name-input]").type("test name");
    cy.get("[data-test-id=update-user-btn]").click();
    cy.get("[data-test-id=full-name-input]")
      .invoke("val")
      .should("contains", "test");
  });
});
