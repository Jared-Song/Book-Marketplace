describe("Navigation", () => {
  it("should navigate to the Login page", () => {
    // Start from the index page
    cy.visit("http://localhost:3000/");

    // Find a link with an href attribute containing "about" and click it
    cy.get('[id="login-btn"]').click();

    // The new url should include "/about"
    cy.url().should("include", "/login");

    // The new page should contain an h1 with "About page"
    cy.get("h5").contains("Login");
    cy.get("h6").contains("Don't have an account");
  });

  it("should navigate to the signup page", () => {
    cy.visit("http://localhost:3000/");
    cy.get('[id="sign-up-btn"]').click();
    cy.url().should("include", "/signup");
    cy.get("h5").contains("Create a New Account");
  })

  it("should search", () => {
    cy.visit("http://localhost:3000/");
    cy.get("[data-test-id=search_input]").type("test");
    cy.get("[data-test-id=search-btn").click()
    cy.url().should("include", "/search?keyword=test");
  })
});
