<<<<<<< HEAD
# Spring Boot Job Management Project

This is a **microservices-based job management application** built using **Spring Boot**. The project implements a scalable architecture by breaking down functionalities into multiple services, each handling a specific responsibility. The primary goal of the application is to manage jobs, companies, users, and reviews efficiently while leveraging a modular design for maintainability and extensibility.

## Key Features
1. **Microservices Architecture**: Each service operates independently and communicates with others using REST APIs.
2. **Core Functionalities**:
   - Job creation, management, and retrieval.
   - User management and job application tracking.
   - Company-specific details and associated reviews.
   - Review management for companies and jobs.
3. **Database Integration**: Services are backed by separate relational databases to store domain-specific data.
4. **Feign Clients**: Simplified inter-service communication through declarative REST API calls.
5. **Scalability and Modularity**: Services can be scaled individually as needed, enabling efficient resource utilization.

## Services
1. **Company Service**: Manages companies and their associated data.
2. **Job Service**: Handles all job-related operations, such as creating, retrieving, and managing job postings.
3. **User Service**: Facilitates user management and job applications tracking.
4. **Review Service**: Enables users to add, manage, and retrieve reviews for companies and jobs.

## Technologies Used
- **Spring Boot**: Core framework for microservices.
- **MySQL**: Relational database for data persistence.
- **Feign**: For inter-service communication.
- **Hibernate**: ORM for database operations.
- **Postman**: API testing.
- **Git**: Version control.

# Job Service API

The **Job Service** is responsible for managing job-related operations, including creating, updating, retrieving, and deleting jobs. It also handles job applications by allowing users to apply or withdraw from job postings.

## API Endpoints

### 1. Retrieve All Jobs
- **Route**: `GET /job-service/jobs`
- **Description**: Fetches all job postings stored in the database.
- **Feign Communication**: None

### 2. Retrieve a Job by ID
- **Route**: `GET /job-service/jobs/{id}`
- **Description**: Fetches details of a specific job using its ID.
- **Feign Communication**: None

### 3. Create a Job
- **Route**: `POST /job-service/jobs`
- **Description**: Creates a new job posting and persists it to the database.
- **Feign Communication**: 
  - **Called by Company Service** when a company creates a new job.
  - `company-service` calls `jobFeign.createJob(job)`

### 4. Update a Job
- **Route**: `PUT /job-service/jobs/{id}`
- **Description**: Updates an existing job posting.
- **Feign Communication**: 
  - **Called by Company Service** when a company updates a job.
  - `company-service` calls `jobFeign.updateJob(jobId, job)`

### 5. Delete a Job
- **Route**: `DELETE /job-service/jobs/{id}`
- **Description**:
  - Deletes a job from the database using its ID.
  - **Handles potential cascading issues** where a deleted job is referenced elsewhere.
- **Feign Communication**: 
  - **Called by Company Service** when a company deletes a job.
  - `company-service` calls `jobFeign.deleteJob(jobId)`

### 6. Fetch Jobs by Company ID
- **Route**: `GET /job-service/jobs/company/{companyId}`
- **Description**: Retrieves all jobs associated with a specific company.
- **Feign Communication**: 
  - **Called by Company Service** when retrieving a company's jobs.
  - `company-service` calls `jobFeign.fetchJobsByIds(jobIds)`

### 7. Fetch Jobs by a List of IDs
- **Route**: `POST /job-service/jobs/company/fetch-jobs-by-ids`
- **Description**: Retrieves multiple job postings based on a provided list of job IDs.
- **Feign Communication**: 
  - **Called by User Service** when fetching jobs a user has applied to.
  - `user-service` calls `jobFeign.fetchJobsByIds(jobIds)`

### 8. Search Jobs by Name
- **Route**: `GET /job-service/jobs/get-jobs-by-name`
- **Query Param**: `jobName`
- **Description**: Retrieves jobs that contain the specified job name (case-insensitive).
- **Feign Communication**: None

### 9. Process a New Applicant for a Job
- **Route**: `POST /job-service/jobs/{jobId}/new-applicant/{userId}`
- **Description**:
  - Allows a user to apply for a job.
  - Updates the list of applicant IDs associated with the job.
- **Feign Communication**:
  - **Called by User Service** when a user applies for a job.
  - `user-service` calls `jobFeign.processNewApplicant(jobId, userId)`

### 10. Withdraw an Applicant from a Job
- **Route**: `POST /job-service/jobs/{jobId}/withdraw-applicant/{userId}`
- **Description**:
  - Allows a user to withdraw their application from a job.
  - Removes the user's ID from the job’s applicant list.
- **Feign Communication**:
  - **Called by User Service** when a user withdraws from a job.
  - `user-service` calls `jobFeign.withdrawApplicant(jobId, userId)`

---

## Dependencies

- **Feign Clients**:
  - `UserFeign`: Used by **User Service** to interact with job applications.
  - `CompanyFeign`: Used by **Company Service** to manage company jobs.

- **Database Interaction**:
  - Uses `JobDao` for all database operations related to jobs.

---

# Company Service API

The **Company Service** manages all operations related to companies, including handling associated jobs and reviews. It also communicates with other services (`Job Service`, `Review Service`, and `User Service`) via Feign clients for additional functionality.

## API Endpoints

### 1. Retrieve All Companies
- **Route**: `GET /company-service/companies`
- **Description**: Fetches a list of all companies.
- **Feign Communication**: None

### 2. Retrieve a Company by ID
- **Route**: `GET /company-service/companies/{id}`
- **Description**: Fetches a company's details using its unique identifier.
- **Feign Communication**: None

### 3. Create a Company
- **Route**: `POST /company-service/companies`
- **Description**: Creates a new company in the system.
- **Feign Communication**: None

### 4. Update a Company
- **Route**: `PUT /company-service/companies/{id}`
- **Description**: Updates the details of a company by its ID.
- **Feign Communication**: None

### 5. Delete a Company
- **Route**: `DELETE /company-service/companies/{id}`
- **Description**:
  - Deletes a company and removes all its associated jobs and reviews.
  - Communicates with:
    - **Job Service**: Deletes jobs tied to the company.
    - **Review Service**: Deletes reviews tied to the company.
- **Feign Communication**:
  - `jobFeign.deleteJob(jobId)`
  - `reviewFeign.deleteReview(reviewId)`

### 6. Create a Job for a Company
- **Route**: `POST /company-service/companies/create-job/{companyId}`
- **Description**:
  - Creates a new job under a specific company.
  - Communicates with the **Job Service** to persist the job details.
- **Feign Communication**:
  - `jobFeign.createJob(job)`

### 7. Fetch Jobs by Company ID
- **Route**: `GET /company-service/companies/{companyId}/jobs`
- **Description**:
  - Retrieves all jobs associated with a specific company.
  - Communicates with the **Job Service** to fetch job details.
- **Feign Communication**:
  - `jobFeign.fetchJobsByIds(jobIds)`

### 8. Update a Job for a Company
- **Route**: `PUT /company-service/companies/{companyId}/{jobId}`
- **Description**:
  - Updates a job under a company if the job belongs to that company.
  - Communicates with the **Job Service** to update job details.
- **Feign Communication**:
  - `jobFeign.updateJob(jobId, job)`

### 9. Delete a Job for a Company
- **Route**: `DELETE /company-service/companies/{companyId}/{jobId}`
- **Description**:
  - Deletes a specific job under a company.
  - Removes the job ID from the company's job list after deletion.
  - Communicates with the **Job Service** to delete the job.
- **Feign Communication**:
  - `jobFeign.deleteJob(jobId)`

### 10. Fetch Reviews by Company ID
- **Route**: `GET /company-service/companies/get-all-reviews/{companyId}`
- **Description**:
  - Retrieves all reviews associated with a specific company.
  - Communicates with the **Review Service** to fetch review details.
- **Feign Communication**:
  - `reviewFeign.fetchReviewsByIds(reviewIds)`

### 11. View Applications for a Job
- **Route**: `GET /company-service/companies/view-applications-for-job/{companyId}/{jobId}`
- **Description**:
  - Retrieves all applications (users) for a specific job under a company.
  - Steps:
    - Verifies if the company and job exist.
    - Fetches applicant IDs from the **Job Service**.
    - Retrieves applicant details from the **User Service**.
- **Feign Communication**:
  - `jobFeign.getJobById(jobId)`
  - `userFeign.getUsersByIds(applicantIds)`

### 12. Retrieve a Company by Name
- **Route**: `GET /company-service/companies/get-company-by-name/{companyName}`
- **Description**: Fetches a company's details by its name.
- **Feign Communication**: None

---

## Dependencies

- **Feign Clients**:
  - `JobFeign`: Interacts with the **Job Service** for job-related operations.
  - `ReviewFeign`: Interacts with the **Review Service** for review-related operations.
  - `UserFeign`: Interacts with the **User Service** for user-related operations.

- **Database Interaction**:
  - Uses `CompanyDao` for all database operations related to companies.

---

# User Service API

The **User Service** is responsible for managing user-related operations, including user creation, profile management, job applications, and leaving reviews for companies. It communicates with the **Job Service** and **Company Service** to provide comprehensive user functionality.

## API Endpoints

### 1. Retrieve a User by ID
- **Route**: `GET /users/{id}`
- **Description**: Fetches details of a specific user using their ID.
- **Feign Communication**: None

### 2. Retrieve Multiple Users by IDs
- **Route**: `POST /users/get-users-by-ids`
- **Description**: Fetches details of multiple users based on a list of user IDs.
- **Feign Communication**: 
  - **Called by Company Service** when retrieving applicants for a job.
  - `company-service` calls `userFeign.getUsersByIds(applicantIds)`

### 3. Create a New User
- **Route**: `POST /users`
- **Description**: Creates a new user and persists them in the database.
- **Feign Communication**: None

### 4. Update a User
- **Route**: `PUT /users/{id}`
- **Description**: Updates an existing user's profile.
- **Feign Communication**: None

### 5. Delete a User
- **Route**: `DELETE /users/{id}`
- **Description**:
  - Deletes a user from the system.
  - **Withdraws the user from all job applications before deletion**.
- **Feign Communication**: 
  - **Calls Job Service** to withdraw the user from all job applications.
  - `user-service` calls `jobFeign.withdrawApplicant(jobId, userId)`

### 6. Fetch Job Applications for a User
- **Route**: `GET /users/{id}/job-applications`
- **Description**: Retrieves all jobs that a user has applied to.
- **Feign Communication**: 
  - **Calls Job Service** to get job details for the applied jobs.
  - `user-service` calls `jobFeign.fetchJobsByIds(jobIds)`

### 7. Fetch Company Information for a User
- **Route**: `GET /users/get-company/{companyId}`
- **Description**:
  - Fetches company details, job listings, and reviews related to the company.
- **Feign Communication**:
  - **Calls Company Service** to get company details.
  - **Calls Job Service** to get jobs related to the company.
  - **Calls Review Service** to get reviews related to the company.
  - `user-service` calls `companyFeign.getCompanyById(companyId)`, `jobFeign.fetchJobsByIds(jobIds)`, `reviewFeign.fetchReviewsByIds(reviewIds)`

### 8. Fetch Company Reviews
- **Route**: `GET /users/get-company-reviews/{companyId}`
- **Description**: Fetches all reviews of a specific company.
- **Feign Communication**:
  - **Calls Company Service** to get review IDs.
  - **Calls Review Service** to fetch the actual reviews.
  - `user-service` calls `reviewFeign.fetchReviewsByIds(reviewIds)`

### 9. Fetch Available Jobs
- **Route**: `GET /users/get-available-jobs`
- **Description**: Retrieves all available job listings.
- **Feign Communication**:
  - **Calls Job Service** to get the list of jobs.
  - `user-service` calls `jobFeign.getAllJobs()`

### 10. Retrieve Job by ID
- **Route**: `GET /users/get-job/{jobId}`
- **Description**: Fetches a job posting using its ID.
- **Feign Communication**:
  - **Calls Job Service** to get job details.
  - `user-service` calls `jobFeign.getJobById(jobId)`

### 11. Fetch Company by Name
- **Route**: `GET /users/get-company-by-name/{companyName}`
- **Description**:
  - Fetches company details along with its job listings and reviews.
- **Feign Communication**:
  - **Calls Company Service** to get company details.
  - **Calls Job Service** to get jobs related to the company.
  - **Calls Review Service** to get reviews related to the company.
  - `user-service` calls `companyFeign.getCompanyByName(companyName)`, `jobFeign.fetchJobsByIds(jobIds)`, `reviewFeign.fetchReviewsByIds(reviewIds)`

### 12. Search Jobs by Name
- **Route**: `GET /users/get-jobs-by-name`
- **Query Param**: `jobName`
- **Description**: Retrieves jobs that contain the specified job name (case-insensitive).
- **Feign Communication**:
  - **Calls Job Service** to search for jobs by name.
  - `user-service` calls `jobFeign.getJobsByName(jobName)`

### 13. Leave a Review for a Company
- **Route**: `POST /users/{userId}/leave-review/{companyId}`
- **Description**:
  - Allows a user to leave a review for their current company.
  - Users can only review companies they are currently employed in.
- **Feign Communication**:
  - **Calls Review Service** to create a review.
  - `user-service` calls `reviewFeign.createReview(review)`

### 14. Apply for a Job
- **Route**: `POST /users/{userId}/apply/{jobId}`
- **Description**:
  - Allows a user to apply for a job.
  - Prevents users from applying to jobs in their own company.
  - Updates both **User Service** (adding job ID) and **Job Service** (adding applicant ID).
- **Feign Communication**:
  - **Calls Job Service** to register the applicant.
  - `user-service` calls `jobFeign.processNewApplicant(jobId, userId)`

### 15. Withdraw from a Job Application
- **Route**: `POST /users/{userId}/withdraw/{jobId}`
- **Description**:
  - Allows a user to withdraw their application for a job.
  - Updates both **User Service** (removing job ID) and **Job Service** (removing applicant ID).
- **Feign Communication**:
  - **Calls Job Service** to remove the user from the job's applicant list.
  - `user-service` calls `jobFeign.withdrawApplicant(jobId, userId)`

---

## Dependencies

- **Feign Clients**:
  - `JobFeign`: Used to interact with job-related operations.
  - `CompanyFeign`: Used to retrieve company-related information.
  - `ReviewFeign`: Used to manage reviews.

- **Database Interaction**:
  - Uses `UserDao` for all database operations related to users.

---

# Review Service API

The **Review Service** is responsible for handling company reviews. Users can leave reviews for companies they have worked at, update their reviews, or delete them. This service communicates with the **Company Service** to fetch company reviews.

## API Endpoints

### 1. Retrieve All Reviews
- **Route**: `GET /review-service/reviews`
- **Description**: Fetches a list of all reviews stored in the database.
- **Feign Communication**: None

### 2. Create a Review
- **Route**: `POST /review-service/reviews`
- **Description**: 
  - Allows users to submit a review for a company.
  - A user can only leave a review for their **current company**.
- **Feign Communication**:
  - **Called by User Service** when a user submits a review.
  - `user-service` calls `reviewFeign.createReview(review)`

### 3. Update a Review
- **Route**: `PUT /review-service/reviews/{id}`
- **Description**: Updates an existing review based on its ID.
- **Feign Communication**: None

### 4. Delete a Review
- **Route**: `DELETE /review-service/reviews/{id}`
- **Description**: Deletes a review from the database.
- **Feign Communication**:
  - **Called by Company Service** when a company is deleted, ensuring its reviews are also removed.
  - `company-service` calls `reviewFeign.deleteReview(reviewId)`

### 5. Fetch Reviews by a List of IDs
- **Route**: `POST /review-service/reviews/fetch-reviews-by-ids`
- **Description**:
  - Fetches multiple reviews based on a list of review IDs.
  - Used when retrieving reviews for a **specific company**.
- **Feign Communication**:
  - **Called by Company Service** when fetching reviews for a company.
  - **Called by User Service** when retrieving company reviews for a user.
  - `company-service` and `user-service` call `reviewFeign.fetchReviewsByIds(reviewIds)`

---

## Dependencies

- **Feign Clients**:
  - `ReviewFeign`: Used by **User Service** and **Company Service** to retrieve and delete reviews.

- **Database Interaction**:
  - Uses `ReviewDao` for all database operations related to reviews.

---

