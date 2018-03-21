package com.mindnotix.eventbus;


public class Events {

    // Event used to send message from fragment to activity.
    public static class FragmentActivityMessage {
        private String message;

        public FragmentActivityMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from activity to activity.
    public static class ActivityRefreshCommandsContributeCount {
        private int count;
        int position;
        String type;

        public ActivityRefreshCommandsContributeCount(int message, int position, String s) {
            this.count = message;
            this.position = position;
            this.type = s;
        }

        public String getType() {
            return type;
        }

        public int getPosition() {
            return position;
        }

        public int getCount() {
            return count;
        }
    }

    public static class AboutFragmentEvent {
      String description;
      String address;
      String contact;
      String region;
      String district;

        public AboutFragmentEvent(String description, String address, String contact, String region, String district) {
            this.description = description;
            this.address = address;
            this.contact = contact;
            this.region = region;
            this.district = district;
        }

        public String getDescription() {
            return description;
        }

        public String getAddress() {
            return address;
        }

        public String getContact() {
            return contact;
        }

        public String getRegion() {
            return region;
        }

        public String getDistrict() {
            return district;
        }
    }
}
