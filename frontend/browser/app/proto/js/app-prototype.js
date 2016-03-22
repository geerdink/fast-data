angular.module('prototype', [
	'ingGlobal',
	'ingComponents',
	'gTopNavigation',
	'gFooter',

	'tgCheckbox',
	'tgCollapsible',
	'tgComparator',
	'tgComparisonTable',
	'tgFullCover',
	'tgIcon',
	'tgImage',
	'tgList',
	'tgRadio',
	'tgRibbon',
	'tgSplitList',
	'tgTabs',
	'tgToggle',
	'tgUtils'

]).directive('topNavigation', ['mqService', '$rootScope', function (mqService, $rootScope) {
	return {
		restrict: 'EA',
		scope: true,
		replace: true,
		link: function(scope, elt, attrs, ctrl) {
			var breakPointWidth = mqService.getBreakPoint('md');
			var tabletWidth = mqService.getBreakPoint('lg');
			$rootScope.$on('window-resize', function(event, docWidth, mqObj){
				var mobileWidth = (mqObj.width < breakPointWidth);
				scope.mobile = mobileWidth;
				scope.tablet = (mqObj.width < tabletWidth);
				scope.$apply();
			});
		}
	}
}]);
