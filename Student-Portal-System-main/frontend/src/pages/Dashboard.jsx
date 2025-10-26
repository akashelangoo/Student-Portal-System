import React, { useEffect, useState } from 'react';
import api from '../services/api'; // eslint-disable-line no-unused-vars

export default function Dashboard() {
  const [courses, setCourses] = useState([]);
  const [myCourses, setMyCourses] = useState([]);
  const [activeTab, setActiveTab] = useState('my-courses');
  const [user, setUser] = useState({});

  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem('user') || '{}');
    setUser(storedUser);
    loadCourses();
    loadMyCourses();
  }, []);

  const loadCourses = async () => {
    try {
      // Mock data - replace with actual API call
      const mockCourses = [
        { id: 1, title: 'Mathematics 101', instructor: 'Dr. Smith', enrolled: false },
        { id: 2, title: 'Computer Science', instructor: 'Prof. Johnson', enrolled: true },
        { id: 3, title: 'Physics Fundamentals', instructor: 'Dr. Brown', enrolled: false },
        { id: 4, title: 'English Literature', instructor: 'Prof. Davis', enrolled: false },
      ];
      setCourses(mockCourses);
    } catch (error) {
      console.log("Error loading courses:", error);
    }
  };

  const loadMyCourses = async () => {
    try {
      // Mock enrolled courses
      const mockMyCourses = [
        { id: 2, title: 'Computer Science', progress: 75, instructor: 'Prof. Johnson' },
        { id: 5, title: 'History 101', progress: 30, instructor: 'Dr. Wilson' },
      ];
      setMyCourses(mockMyCourses);
    } catch (error) {
      console.log("Error loading my courses:", error);
    }
  };

  const enrollInCourse = async (courseId) => {
    try {
      alert(`Successfully enrolled in course!`);
      
      // Update the course as enrolled
      setCourses(prevCourses => 
        prevCourses.map(course => 
          course.id === courseId 
            ? { ...course, enrolled: true }
            : course
        )
      );
      
      // Add to my courses
      const enrolledCourse = courses.find(course => course.id === courseId);
      if (enrolledCourse) {
        setMyCourses(prev => [
          ...prev,
          { 
            ...enrolledCourse, 
            progress: 0 
          }
        ]);
      }
      
    } catch (error) {
      alert('Error enrolling in course: ' + error.message);
    }
  };

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h1>Welcome back, {user.name}!</h1>
        <p>Role: {user.role}</p>
      </div>

      <div className="tabs">
        <button 
          className={activeTab === 'my-courses' ? 'tab-active' : 'tab'}
          onClick={() => setActiveTab('my-courses')}
        >
          My Courses
        </button>
        <button 
          className={activeTab === 'all-courses' ? 'tab-active' : 'tab'}
          onClick={() => setActiveTab('all-courses')}
        >
          All Courses
        </button>
      </div>

      {activeTab === 'my-courses' && (
        <div className="courses-grid">
          {myCourses.map(course => (
            <div key={course.id} className="course-card enrolled">
              <h3>{course.title}</h3>
              <p>Instructor: {course.instructor}</p>
              <div className="progress-bar">
                <div 
                  className="progress-fill" 
                  style={{ width: `${course.progress}%` }}
                ></div>
              </div>
              <p>Progress: {course.progress}%</p>
              <button className="continue-btn">Continue Learning</button>
            </div>
          ))}
        </div>
      )}

      {activeTab === 'all-courses' && (
        <div className="courses-grid">
          {courses.map(course => (
            <div key={course.id} className="course-card">
              <h3>{course.title}</h3>
              <p>Instructor: {course.instructor}</p>
              <button 
                className={course.enrolled ? 'enrolled-btn' : 'enroll-btn'}
                onClick={() => !course.enrolled && enrollInCourse(course.id)}
                disabled={course.enrolled}
              >
                {course.enrolled ? 'Enrolled' : 'Enroll Now'}
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
