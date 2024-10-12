    package controller;

    import model.User;
    import org.example.assignment1.Encoder;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import repository.UserRepository;
    import service.UserService;

    import java.util.List;
    import java.util.stream.Collectors;

    /**
     * Controller class for managing user-related operations.
     */
    @Controller
    @RequestMapping("/api/users")
    public class UserController {

        private Encoder encoder = Encoder.getInstance();
        @Autowired
        private UserService userService;

        /**
         * Displays the index page
         *
         * @param model The model to be populated with data.
         * @return The name of the index HTML file.
         */
        @GetMapping("/")
        public String index(Model model) {
            return "index";
        }

        /**
         * Displays the registration page.
         *
         * @param model The model to be populated with data.
         * @return The name of the register HTML file.
         */
        @GetMapping("/register.html")
        public String showRegisterPage(Model model) {
            return "register";
        }

        /**
         * Registers a new user.
         *
         * @param user The user object containing registration details.
         * @return ResponseEntity indicating the status of the registration process.
         */
        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody User user) {
            try {
                // Encode the user's password before saving to the database
                user.setPassword(Encoder.encodingPassword(user.getPassword()));
                userService.registerUser(user);
                return ResponseEntity.ok("User registered successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user: " + e.getMessage());
            }
        }

        /**
         * Displays the login page.
         *
         * @return The name of the login HTML file.
         */
        @GetMapping("/login.html")
        public String showLoginPage() {
            SecurityVariable.isUserLoggedIn = false;
            SecurityVariable.isUserNormal = false;
            SecurityVariable.isUserAdmin = false;
            SecurityVariable.loggedUserId=-1;
            return "login";
        }

        /**
         * Displays the not logged in page.
         *
         * @return The name of the not logged in HTML file.
         */
        @GetMapping("/not_logged_in.html")
        public String showNotLoggedInPage() {
            return "not_logged_in";
        }

        /**
         * Logs in a user.
         *
         * @param user The user object containing login details.
         * @return ResponseEntity indicating the status of the login process.
         */
        @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody User user) {
            boolean isValidUser = userService.validateUserCredentials(user.getUsername(), Encoder.encodingPassword(user.getPassword()));
            if (isValidUser) {
                SecurityVariable.isUserLoggedIn = true;
                String token = userService.loginUser(user);

                String userRole = userService.getUserRole(user.getUsername());
                Long userId = userService.getUserIdByUsername(user.getUsername());
                SecurityVariable.loggedUserId=userId;
                String dashboardUrl;
                if ("Normal".equals(userRole)) {
                    dashboardUrl = "/api/users/normal_dashboard.html?userId=" + userId;
                    SecurityVariable.isUserNormal = true;
                } else if ("Admin".equals(userRole)) {
                    dashboardUrl = "/api/users/admin_dashboard.html?userId=" + userId;
                    SecurityVariable.isUserAdmin = true;
                } else {
                    dashboardUrl = "/api/users/normal_dashboard.html?userId=" + userId;
                    SecurityVariable.isUserNormal = true;
                }
                return ResponseEntity.status(HttpStatus.OK).body(dashboardUrl);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        }

        /**
         * Displays the normal dashboard if the user is logged in and is a normal user,
         * otherwise redirects to the not logged in page.
         *
         * @return The name of the normal dashboard HTML file or a redirection URL.
         */
        @GetMapping("/normal_dashboard.html")
        public String showNormalDashboard() {
            if(SecurityVariable.isUserLoggedIn && SecurityVariable.isUserNormal) {
                return "normal_dashboard";
            } else {
                return "redirect:/api/users/not_logged_in.html";
            }
            //return "normal_dashboard";
        }

        /**
         * Displays the admin dashboard if the user is logged in and is an admin user,
         * otherwise redirects to the not logged in page.
         *
         * @return The name of the admin dashboard HTML file or a redirection URL.
         */
        @GetMapping("/admin_dashboard.html")
        public String showAdminDashboard() {
            if(SecurityVariable.isUserLoggedIn && SecurityVariable.isUserAdmin) {
                return "admin_dashboard";
            } else {
                return "redirect:/api/users/not_logged_in.html";
            }
           // return "admin_dashboard";
        }

        /**
         * Retrieves a user by their ID.
         *
         * @param userId The ID of the user to retrieve.
         * @return ResponseEntity containing the user if found, or an error response if not found.
         */
        @GetMapping("/user")
        public ResponseEntity<User> getUserById(@RequestParam Long userId) {
            try {
                // Fetch the user by ID
                User user = userService.getUserById(userId);
                return ResponseEntity.ok(user);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null);
            }
        }

        /**
         * Retrieves a user by their ID for messaging purposes.
         *
         * @param userId The ID of the user to retrieve.
         * @return ResponseEntity containing the user if found, or an error response if not found.
         */
        @GetMapping("/get_user_by_id/{userId}")
        public ResponseEntity<User> getUserByIdForMessage(@PathVariable Long userId) {
            try {
                // Fetch the user by ID
                User user = userService.getUserById(userId);
                return ResponseEntity.ok(user);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null);
            }
        }

        /**
         * Displays the admin user management page if the user is logged in and is an admin user,
         * otherwise redirects to the not logged in page.
         *
         * @param model The model to be populated with data.
         * @return The name of the admin user management HTML file or a redirection URL.
         */
        @GetMapping("/admin_user_management.html")
        public String showAdminUserManagement(Model model) {
            // Fetch all users from the database
            List<User> users = userService.getAllUsers();

            model.addAttribute("users", users);
          //  return "admin_user_management";
            if(SecurityVariable.isUserLoggedIn && SecurityVariable.isUserAdmin) {
                return "admin_user_management";
            } else {
                return "redirect:/api/users/not_logged_in.html";
            }
        }

        /**
         * Displays the create user page if the user is logged in and is an admin user,
         * otherwise redirects to the not logged in page.
         *
         * @param model The model to be populated with data.
         * @return The name of the create user HTML file or a redirection URL.
         */
        @GetMapping("/create_user.html")
        public String showCreateUser(Model model) {
            List<User> users = userService.getAllUsers();
            for (User user : users) {
                System.out.println(user.getUser_id());
            }
            model.addAttribute("users", users);
           // return "create_user";
            if(SecurityVariable.isUserLoggedIn && SecurityVariable.isUserAdmin) {
                return "create_user";
            } else {
                return "redirect:/api/users/not_logged_in.html";
            }
        }

        /**
         * Creates a new user.
         *
         * @param user The user object containing registration details.
         * @return ResponseEntity indicating the status of the user creation process.
         */
        @PostMapping("/create_user")
        public ResponseEntity<String> createUser(@RequestBody User user) {
            try {
                user.setPassword(Encoder.encodingPassword(user.getPassword()));
                userService.createUser(user);

              //  System.out.println(user.getUsername());
              //  System.out.println(user.getPassword());
                return ResponseEntity.ok("User created successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user: " + e.getMessage());
            }
        }

        /**
         * Displays the update user page if the user is logged in and is an admin user,
         * otherwise redirects to the not logged in page.
         *
         * @param model The model to be populated with data.
         * @return The name of the update user HTML file or a redirection URL.
         */
        @GetMapping("/update_user.html")
        public String showUpdateUser(Model model) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
          //  return "update_user";
            if(SecurityVariable.isUserLoggedIn && SecurityVariable.isUserAdmin) {
                return "update_user";
            } else {
                return "redirect:/api/users/not_logged_in.html";
            }
        }

        /**
         * Updates an existing user.
         *
         * @param userId The ID of the user to update.
         * @param user   The updated user object.
         * @return ResponseEntity indicating the status of the user update process.
         */
        @PutMapping("/update_user/{userId}")
        public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody User user) {
            try {
                user.setPassword(Encoder.encodingPassword(user.getPassword()));
                userService.updateUser(userId, user);

                System.out.println(user.getPassword());
                return ResponseEntity.ok("User updated successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user: " + e.getMessage());
            }
        }

        /**
         * Displays the delete user page if the user is logged in and is an admin user,
         * otherwise redirects to the not logged in page.
         *
         * @param model The model to be populated with data.
         * @return The name of the delete user HTML file or a redirection URL.
         */
        @GetMapping("/delete_user.html")
        public String showDeleteUser(Model model) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
           // return "delete_user";
            if(SecurityVariable.isUserLoggedIn && SecurityVariable.isUserAdmin) {
                return "delete_user";
            } else {
                return "redirect:/api/users/not_logged_in.html";
            }
        }

        /**
         * Deletes a user.
         *
         * @param userId The ID of the user to delete.
         * @return ResponseEntity indicating the status of the user deletion process.
         */
        @DeleteMapping("/delete_user/{userId}")
        public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
            try {
                userService.deleteUser(userId);
                // List<User> users = userService.getAllUsers();
                //model.addAttribute("users", users);
                return ResponseEntity.ok("User deleted successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user: " + e.getMessage());
            }
        }

        /**
         * Displays the update account form.
         *
         * @param userId The ID of the user whose account is being updated.
         * @param model  The model to be populated with data.
         * @return The name of the update account HTML file or a redirection URL.
         */
        @GetMapping("/update_account.html")
        public String showUpdateAccountForm(@RequestParam Long userId, Model model) {
            // Fetch the user based on the userId
            User user = userService.getUserById(userId);
           // System.out.println(user.getUser_id());
          //  System.out.println(user.getPassword());
            user.setPassword(Encoder.decodingPassword(user.getPassword()));
          //  System.out.println(user.getPassword());
            // Add the user to the model
            model.addAttribute("user", user);

            // Return the name of the HTML file
           // return "update_account";
            if(SecurityVariable.isUserLoggedIn) {
                return "update_account";
            } else {
                return "redirect:/api/users/not_logged_in.html";
            }
        }

        /**
         * Updates the user account.
         *
         * @param userId The ID of the user whose account is being updated.
         * @param user   The updated user object.
         * @return ResponseEntity indicating the status of the user account update process.
         */
        @PutMapping("/update_account/{userId}")
        public ResponseEntity<String> updateAccount(@PathVariable Long userId, @RequestBody User user) {
            try {
                // Fetch the existing user by ID
                User existingUser = userService.getUserById(userId);

                // Update user fields if provided in the request body
                if (user.getUsername() != null && !user.getUsername().isEmpty()) {
                    existingUser.setUsername(user.getUsername());
                }
                if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                    existingUser.setPassword(Encoder.encodingPassword(user.getPassword()));
                }

                // Save the updated user
                userService.updateUser(userId, existingUser);

                return ResponseEntity.ok("User account updated successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update user account: " + e.getMessage());
            }
        }

    }
