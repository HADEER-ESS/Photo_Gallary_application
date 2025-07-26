<h1>Application Required Screenshots</h1>

<h3>Online Dark Mode</h3>
<img width="350" height="1000" alt="online_darkMode" src="https://github.com/user-attachments/assets/bd49f781-8356-4beb-aad7-1ea2478f10ec" />

<h3>Online Light Mode</h3>
<img width="350" height="1000" alt="online_lightMode" src="https://github.com/user-attachments/assets/da2a80ab-16d4-48ca-be87-622c7a4a88bf" />


<h3>Offline Dark Mode</h3>
<img width="350" height="1000" alt="offline_darkMode" src="https://github.com/user-attachments/assets/11ac86d3-e97e-4ee1-9944-eda540c59fa0" />

<h3>Offline Light Mode</h3>
<img width="350" height="1000" alt="offline_lightMode" src="https://github.com/user-attachments/assets/56a5d276-5be8-4be9-9874-e68882d987ff" />


<h3>Loading State</h3>
<img width="350" height="1000" alt="loading_state" src="https://github.com/user-attachments/assets/b94b4b6d-830d-4d56-8f06-d21bd234a543" />

<h3>Error State</h3>
<img width="350" height="1000" alt="error_state" src="https://github.com/user-attachments/assets/5065e074-8f5f-4350-a408-8c43824bafb9" />

<h3>Network Status Indicator</h3>
<img width="350" height="1000" alt="Network_indicator" src="https://github.com/user-attachments/assets/6978c973-f6bd-4580-becf-d2f8ecf12e38" />




<h3>A detailed explanation of the App</h3>
<p>
  <h5>Architecture: MVI</h5>
  <br/>
  <h5>
    Clean Architecture Layer:
  </h5>
  <li>Domain: for Entities, Repositories(interface), UseCases</li>
  <li>Data: for Repositories implementation Logic, API services, Application Database instance implementation, helper utilities (Theme-Preference, Check Internet Connectivity)</li>
  <li>
      App: for Dependency Injection, ViewModels, and UI
  </li>

  <h5>
     Cache: Room Database: 
  </h5>
<li>
  Store the image URL as a file in the Database to avoid any API calls
</li>
<h5>
  Navigation Component
</h5>
<li>
  Create two fragments in one activity and navigate through them (splash, home)
</li>
  <h5>
    Glide
  </h5>

  <li>
     Use for rendering an image from a URL, or from cache as a File string
  </li>
</p>

<h3>Rendered Data Conditions</h3>
<h5> User enters screen check (internet, cache data)  </h5>
<li> → if no internet AND no data in cache → show error text message </li>
<li> → if no internet AND there is data in cache → data sends to Adaptor, and render photos </li>
<li> → if there is internet AND no data in cache → call API and send data to Adaptor </li>
<li> → if there is internet AND there is data in cache → data sends to Adaptor from cache </li>

<h3>Project installation requirements</h3>
<h6>min SDK 24</h6>
<h6>Internet connectivity (for first time)</h6>

<h3>How to Run Locally</h3>
<li>Clone: git clone https://github.com/your-username/your-app.git</li>
<li>Click on the folder, and open it with Android Studio</li>
<li>Sync gradle: File > Sync Project with Gradle Files</li>
<li> Run the app: use Emulator, or Physical device</li>


<h3>Folder Structure</h3>
<img width="777" height="282" alt="Screenshot from 2025-07-27 01-08-05" src="https://github.com/user-attachments/assets/a25ed683-1f9e-4e60-a8ea-b5440bbc3164" />
















































