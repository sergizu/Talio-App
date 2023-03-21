#### Focused Commits
_Very Good_

The amount of commits is reasonable, and they have a clear and consise message one-liners. Some commits are very small, like b17f23f3, where only whitespaces are removed. This can be combined in another commit and not be seperate. For the rest the commits are looking good!


#### Isolation
Features are being developed on their corresponding feature branches which is good to see. However some branch names, like: "Imp2", is  not a valid branchname. And the "FixDatabase" branch should not contain changes in the controller classes.

#### Reviewability
_Good_

Almost all MR's have a clear title and description. MR !53 should have a smaller title. If it needs to be that long, maybe the MR should have been a bit smaller. MR !52 has a title "Fix Database", but contains changed in the controllers. This should have been split into 2 seperate MR's. For the rest the MR's are looking good!

#### Code Reviews
_Very Good_

The MR's are all receiving code reviews which is good to see. They lead to small discussions and iterative improvements in the MR. Some MR (like !50), did not receive any review. Even a single LGTM is better than no review. Comments are also constructive!

#### Build Server
_Good_

There are frequent pushes which activate the build server. They fail sometimes, and are mostly directly fixed, but not always (like pipeline #828608). There are no failing branches, and there are 10+ checkstyle rules added.

